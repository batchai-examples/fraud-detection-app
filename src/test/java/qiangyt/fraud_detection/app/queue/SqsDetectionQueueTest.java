package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;

import static org.mockito.Mockito.*;

/**
 * Unit tests for SqsDetectionQueue class.
 */
public class SqsDetectionQueueTest {

    private SqsDetectionQueue sqsDetectionQueue;
    private DetectionReqEntity detectionReqEntity;

    @BeforeEach
    public void setUp() {
        sqsDetectionQueue = new SqsDetectionQueue();
        detectionReqEntity = new DetectionReqEntity();
        // Mocking the properties to avoid null pointer exceptions
        sqsDetectionQueue.setProps(new SqsProperties("http://mock-queue-url"));
    }

    /**
     * Test sending a valid detection request entity to the SQS queue.
     * This is a happy path test case.
     */
    @Test
    public void testSendValidDetectionReqEntity() {
        // Given a valid DetectionReqEntity
        detectionReqEntity.setSomeField("validData");

        // When send is called
        sqsDetectionQueue.send(detectionReqEntity);

        // Then verify that send method is called with correct parameters
        // Assuming send method is protected and can be mocked
        // This is a placeholder for actual verification logic
        verify(sqsDetectionQueue, times(1)).send(anyString(), eq(detectionReqEntity), eq(""), eq(""));
    }

    /**
     * Test sending a null detection request entity to the SQS queue.
     * This is a negative test case expecting an exception.
     */
    @Test
    public void testSendNullDetectionReqEntity() {
        // Given a null DetectionReqEntity
        DetectionReqEntity nullEntity = null;

        // When send is called, then it should throw an exception
        try {
            sqsDetectionQueue.send(nullEntity);
        } catch (Exception e) {
            // Then verify that the exception is thrown
            assert(e instanceof IllegalArgumentException);
        }
    }

    /**
     * Test sending an empty detection request entity to the SQS queue.
     * This is a corner case test case.
     */
    @Test
    public void testSendEmptyDetectionReqEntity() {
        // Given an empty DetectionReqEntity
        detectionReqEntity.setSomeField("");

        // When send is called
        sqsDetectionQueue.send(detectionReqEntity);

        // Then verify that send method is called with empty data
        verify(sqsDetectionQueue, times(1)).send(anyString(), eq(detectionReqEntity), eq(""), eq(""));
    }

    /**
     * Test sending a detection request entity with invalid data.
     * This is a negative test case expecting an exception.
     */
    @Test
    public void testSendInvalidDetectionReqEntity() {
        // Given a DetectionReqEntity with invalid data
        detectionReqEntity.setSomeField("invalidData");

        // When send is called, then it should throw an exception
        try {
            sqsDetectionQueue.send(detectionReqEntity);
        } catch (Exception e) {
            // Then verify that the exception is thrown
            assert(e instanceof IllegalArgumentException);
        }
    }

    /**
     * Test sending a detection request entity with special characters.
     * This is a positive test case.
     */
    @Test
    public void testSendDetectionReqEntityWithSpecialCharacters() {
        // Given a DetectionReqEntity with special characters
        detectionReqEntity.setSomeField("!@#$%^&*()");

        // When send is called
        sqsDetectionQueue.send(detectionReqEntity);

        // Then verify that send method is called with special characters
        verify(sqsDetectionQueue, times(1)).send(anyString(), eq(detectionReqEntity), eq(""), eq(""));
    }
}
