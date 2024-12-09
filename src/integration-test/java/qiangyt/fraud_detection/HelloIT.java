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
package qiangyt.fraud_detection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.client.v1.client.rest.AppClient;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import qiangyt.fraud_detection.framework.aws.sqs.SqsConfig;
import qiangyt.fraud_detection.framework.json.JacksonHelper;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionResult;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class HelloIT {

    public static final String BASE_URL = "http://localhost:8080";
    
    static final SqsClient sqsClient; 
    static {
        var config = new SqsConfig();
        config.setProps(new AwsProps());
        sqsClient = config.createClient(config.getRegion());
    }

    static final AppClient appClient;
    static {
        appClient = new AppClient(BASE_URL);
        appClient.init();
    }
    
    static final SqsProps sqsProps = new SqsProps(); 
    static {
        sqsProps.setTimeout(2); // decreases the polling timeout
    }

    public static void main(String[] args) {
        new HelloIT().test();
    }

    @BeforeEach 
    @AfterEach
    public void beforeTest() {
        var detectQueueUrl = sqsProps.getDetectQueueUrl();
        clearQueue(detectQueueUrl);

        var alertQueueUrl = sqsProps.getAlertQueueUrl();;
        clearQueue(alertQueueUrl);
    }


    @Test
    public void test() {
        // first, sends a non-fraud request
        var nonFraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999)
                    .memo("N/A")
                    .build();
        var nonFraudEntity = appClient.submit(nonFraudReq);
        dump("non-fraud entity: ", nonFraudEntity);

        // no alert
        assertNull(pollAlert());

        // second, sends a fraud request
        var fraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999 * 100000)
                    .memo("N/A")
                    .build();
        var fraudEntity = appClient.submit(fraudReq);
        dump("fraud entity: ", fraudEntity);

        // got alert
        assertNotNull(pollAlert());
    }

    static int i = 0;

    void dump(String hint, Object obj) {
        System.out.printf("\n%02d. %s:\n%s\n", ++i, hint, JacksonHelper.pretty(obj));
    }

    void clearQueue(String queueUrl) {
        var req = ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(sqsProps.getBatchSize())
                        .waitTimeSeconds(sqsProps.getTimeout())
                        .build();

        var msgList = sqsClient.receiveMessage(req).messages();
        for (var msg: msgList) {
            sqsClient.deleteMessage(
                    DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(msg.receiptHandle())
                            .build());
        }      
    }

    DetectionResult pollAlert() {        
        var alertQueueUrl = sqsProps.getAlertQueueUrl();

        var req = ReceiveMessageRequest.builder()
                        .queueUrl(alertQueueUrl)
                        .maxNumberOfMessages(sqsProps.getBatchSize())
                        .waitTimeSeconds(sqsProps.getTimeout())
                        .build();

        var msgList = sqsClient.receiveMessage(req).messages();
        if (msgList.isEmpty()) {
            return null;
        }

        var msg = msgList.get(0);

        sqsClient.deleteMessage(
                    DeleteMessageRequest.builder()
                            .queueUrl(alertQueueUrl)
                            .receiptHandle(msg.receiptHandle())
                            .build());
   
        String body = msg.body();
        return JacksonHelper.from(body, DetectionResult.class);        
    }
}