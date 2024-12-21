package qiangyt.fraud_detection.app.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.app.queue.SqsDetectionDeadLetterQueue;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetectionSqsControllerTest {

    @Mock
    private SqsProps props;

    @Mock
    private SqsClient client;

    @Mock
    private Jackson jackson;

    @Mock
    private DetectionService service;

    @Mock
    private ExecutorService sqsPollingThreadPool;

    @Mock
    private SqsDetectionDeadLetterQueue deadLetterQueue;

    @InjectMocks
    private DetectionSqsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DetectionSqsController();
        controller.setPolling(new AtomicBoolean(false));
    }

    /** 
     * Test the happy path where messages are successfully processed from the SQS queue.
     */
    @Test
    void testPollOne_HappyPath() throws Exception {
        // Arrange
        String queueUrl = "http://example.com/queue";
        when(props.getDetectQueueUrl()).thenReturn(queueUrl);
        when(props.getBatchSize()).thenReturn(10);
        when(props.getTimeout()).thenReturn(20);
        
        DetectionReqEntity entity = new DetectionReqEntity();
        String messageBody = "message body";
        Message message = Message.builder().body(messageBody).receiptHandle("receiptHandle").build();
        
        when(client.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(
            software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse.builder()
                .messages(Collections.singletonList(message))
                .build()
        );
        when(jackson.from(messageBody, DetectionReqEntity.class)).thenReturn(entity);

        // Act
        controller.pollOne();

        // Assert
        verify(service).detectThenAlert(entity);
        verify(client).deleteMessage(any());
    }

    /** 
     * Test the case where an exception occurs during message processing.
     */
    @Test
    void testPollOne_ExceptionDuringProcessing() throws Exception {
        // Arrange
        String queueUrl = "http://example.com/queue";
        when(props.getDetectQueueUrl()).thenReturn(queueUrl);
        when(props.getBatchSize()).thenReturn(10);
        when(props.getTimeout()).thenReturn(20);
        
        String messageBody = "message body";
        Message message = Message.builder().body(messageBody).receiptHandle("receiptHandle").build();
        
        when(client.receiveMessage(any(ReceiveMessageRequest.class))).thenReturn(
            software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse.builder()
                .messages(Collections.singletonList(message))
                .build()
        );
        when(jackson.from(messageBody, DetectionReqEntity.class)).thenThrow(new RuntimeException("Deserialization error"));

        // Act
        controller.pollOne();

        // Assert
        verify(deadLetterQueue).send(messageBody);
        verify(client).deleteMessage(any());
    }

    /** 
     * Test the case where the SQS client throws an exception during message reception.
     */
    @Test
    void testPollOne_ExceptionDuringReceive() {
        // Arrange
        String queueUrl = "http://example.com/queue";
        when(props.getDetectQueueUrl()).thenReturn(queueUrl);
        when(props.getBatchSize()).thenReturn(10);
        when(props.getTimeout()).thenReturn(20);
        
        when(client.receiveMessage(any(ReceiveMessageRequest.class))).thenThrow(new RuntimeException("SQS error"));

        // Act
        controller.pollOne();

        // Assert
        verify(deadLetterQueue, never()).send(any());
    }

    /** 
     * Test the polling start and stop methods.
     */
    @Test
    void testStartAndStopPolling() {
        // Act
        controller.start();
        // Assert
        verify(sqsPollingThreadPool).submit(any());

        // Act
        controller.stop();
        // Assert
        verify(sqsPollingThreadPool).shutdown();
    }
}
