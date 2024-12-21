package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Test class for SqsDetectionDeadLetterQueue.
 */
public class SqsDetectionDeadLetterQueueTest {

    private SqsDetectionDeadLetterQueue sqsDetectionDeadLetterQueue;

    @BeforeEach
    public void setUp() {
        sqsDetectionDeadLetterQueue = Mockito.spy(new SqsDetectionDeadLetterQueue());
    }

    /**
     * Test case for sending a valid message to the SQS detection dead letter queue.
     * This is a happy path scenario where the message is sent successfully.
     */
    @Test
    public void testSend_ValidMessage() {
        String validMessage = "Test message";

        // Call the method to test
        sqsDetectionDeadLetterQueue.send(validMessage);

        // Verify that the send method was called with the correct parameters
        verify(sqsDetectionDeadLetterQueue, times(1)).send(Mockito.anyString(), Mockito.eq(validMessage), Mockito.eq(""), Mockito.eq(""));
    }

    /**
     * Test case for sending an empty message to the SQS detection dead letter queue.
     * This tests the behavior when an empty string is passed as a message.
     */
    @Test
    public void testSend_EmptyMessage() {
        String emptyMessage = "";

        // Call the method to test
        sqsDetectionDeadLetterQueue.send(emptyMessage);

        // Verify that the send method was called with the correct parameters
        verify(sqsDetectionDeadLetterQueue, times(1)).send(Mockito.anyString(), Mockito.eq(emptyMessage), Mockito.eq(""), Mockito.eq(""));
    }

    /**
     * Test case for sending a null message to the SQS detection dead letter queue.
     * This tests the behavior when a null value is passed as a message.
     */
    @Test
    public void testSend_NullMessage() {
        String nullMessage = null;

        // Call the method to test
        sqsDetectionDeadLetterQueue.send(nullMessage);

        // Verify that the send method was called with the correct parameters
        verify(sqsDetectionDeadLetterQueue, times(1)).send(Mockito.anyString(), Mockito.eq(nullMessage), Mockito.eq(""), Mockito.eq(""));
    }

    /**
     * Test case for sending a long message to the SQS detection dead letter queue.
     * This tests the behavior when a very long string is passed as a message.
     */
    @Test
    public void testSend_LongMessage() {
        String longMessage = "A".repeat(256); // Assuming the limit is less than 256 characters

        // Call the method to test
        sqsDetectionDeadLetterQueue.send(longMessage);

        // Verify that the send method was called with the correct parameters
        verify(sqsDetectionDeadLetterQueue, times(1)).send(Mockito.anyString(), Mockito.eq(longMessage), Mockito.eq(""), Mockito.eq(""));
    }

    /**
     * Test case for sending a message with special characters to the SQS detection dead letter queue.
     * This tests the behavior when a message contains special characters.
     */
    @Test
    public void testSend_SpecialCharactersMessage() {
        String specialCharactersMessage = "!@#$%^&*()_+";

        // Call the method to test
        sqsDetectionDeadLetterQueue.send(specialCharactersMessage);

        // Verify that the send method was called with the correct parameters
        verify(sqsDetectionDeadLetterQueue, times(1)).send(Mockito.anyString(), Mockito.eq(specialCharactersMessage), Mockito.eq(""), Mockito.eq(""));
    }
}
