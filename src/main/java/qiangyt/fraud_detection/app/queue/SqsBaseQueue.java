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
package qiangyt.fraud_detection.app.queue;

import org.springframework.beans.factory.annotation.Autowired;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.framework.misc.StringHelper;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

/**
 * Abstract base class for SQS queues.
 *
 * @param <T> the type of the data to be sent to the queue
 */
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
public abstract class SqsBaseQueue<T> {

    @Autowired SqsProps props;

    @Autowired SqsClient client;

    @Autowired Jackson jackson;

    /**
     * Sends a message to the specified SQS queue.
     *
     * @param queueUrl the URL of the SQS queue
     * @param data the data to be sent
     * @param deduplicationId the deduplication ID for the message (optional)
     * @param messageGroupId the message group ID for the message (optional)
     */
    protected void send(String queueUrl, T data, String deduplicationId, String messageGroupId) {
        // Convert the data object to a JSON string
        var msg = getJackson().str(data);

        // Build the SQS request with the queue URL and message body
        var sqsReqBuilder = SendMessageRequest.builder().queueUrl(queueUrl).messageBody(msg);

        // Add deduplication ID if provided
        if (!StringHelper.isBlank(deduplicationId)) {
            sqsReqBuilder = sqsReqBuilder.messageDeduplicationId(deduplicationId);
        }

        // Add message group ID if provided
        if (!StringHelper.isBlank(messageGroupId)) {
            sqsReqBuilder = sqsReqBuilder.messageGroupId(messageGroupId);
        }

        // Build the final SQS request
        var sqsReq = sqsReqBuilder.build();

        // Send the message to the SQS queue
        getClient().sendMessage(sqsReq);
    }
}
