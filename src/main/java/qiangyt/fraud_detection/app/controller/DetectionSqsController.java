/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.app.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.app.queue.SqsDetectionDeadLetterQueue;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

/** Controller for handling SQS messages related to fraud detection. */
@Service
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
public class DetectionSqsController {

    @Autowired SqsProps props;

    @Autowired SqsClient client;

    @Autowired Jackson jackson;

    @Autowired DetectionService service;

    @Autowired ExecutorService sqsPollingThreadPool;

    final AtomicBoolean polling = new AtomicBoolean();

    @Autowired SqsDetectionDeadLetterQueue deadLetterQueue;

    /** Initializes the SQS polling by submitting the poll task to the thread pool. */
    @PostConstruct
    void start() {
        getSqsPollingThreadPool().submit(this::poll);
    }

    /** Stops the SQS polling and shuts down the thread pool. */
    @PreDestroy
    void stop() {
        log.info("Stopping SQS detection queue polling");
        getPolling().set(false);

        getSqsPollingThreadPool().shutdown();
    }

    /** Polls the SQS queue continuously until stopped. */
    void poll() {
        getPolling().set(true);

        log.info("SQS detection queue polling: started");

        while (getPolling().get()) {
            try {
                pollOne();
            } catch (IllegalStateException ex) {
                if (ex.getMessage().equals("Connection pool shut down")) {
                    break;
                }

                // sleep for a while to avoid busy loop
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            } catch (Exception ex) {
                log.error("Error in polling", ex);
            }
        }

        log.info("SQS detection queue polling: stopped");
    }

    /**
     * Polls a single batch of messages from the SQS queue and processes them.
     *
     * <p>We don't use scheduled job, instead, we use a thread to poll the queue continuously and
     * depends on Sqs's long polling feature
     */
    // @Scheduled(fixedRate = 5000) // 5 seconds polling interval
    void pollOne() {
        var p = getProps();
        var qurl = p.getDetectQueueUrl();
        var s = getService();
        var j = getJackson();

        // Create the request to receive messages from the SQS queue
        var sqsReq =
                ReceiveMessageRequest.builder()
                        .queueUrl(qurl)
                        .maxNumberOfMessages(p.getBatchSize())
                        .waitTimeSeconds(p.getTimeout()) // Long polling
                        .build();

        // Process each received message
        for (var msg : client.receiveMessage(sqsReq).messages()) {
            String body = msg.body();

            try {
                // Deserialize the message body into a DetectionReqEntity
                var entity = j.from(body, DetectionReqEntity.class);

                // Perform detection and alert based on the entity
                s.detectThenAlert(entity);
            } catch (Exception ex) {
                log.error("Error processing message: " + body, ex);

                // Send the message to the dead letter queue if an error occurs
                getDeadLetterQueue().send(body);
            }

            // Delete the message from the queue after processing
            client.deleteMessage(
                    DeleteMessageRequest.builder()
                            .queueUrl(qurl)
                            .receiptHandle(msg.receiptHandle())
                            .build());
        }
    }
}
