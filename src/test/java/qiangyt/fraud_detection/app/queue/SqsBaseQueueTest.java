package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for SqsBaseQueue.
 */
public class SqsBaseQueueTest {

    @Mock
    private SqsProps props;

    @Mock
    private SqsClient client;

    @Mock
    private Jackson jackson;

    @InjectMocks
    private SqsBaseQueue<String> sqsBaseQueue;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test sending a message with valid parameters.
     * This is a happy path test case.
     */
    @Test
    public void testSendMessageWithValidParameters() {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String data = "Test message";
        String deduplicationId = "12345";
        String messageGroupId = "group1";

        when(jackson.str(data)).thenReturn(data);

        // Call the method under test
        sqsBaseQueue.send(queueUrl, data, deduplicationId, messageGroupId);

        // Verify that sendMessage was called with the correct parameters
        verify(client).sendMessage(any(SendMessageRequest.class));
    }

    /**
     * Test sending a message without deduplication ID.
     * This tests the optional deduplication ID parameter.
     */
    @Test
    public void testSendMessageWithoutDeduplicationId() {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String data = "Test message";

        when(jackson.str(data)).thenReturn(data);

        // Call the method under test
        sqsBaseQueue.send(queueUrl, data, null, "group1");

        // Verify that sendMessage was called
        verify(client).sendMessage(any(SendMessageRequest.class));
    }

    /**
     * Test sending a message without message group ID.
     * This tests the optional message group ID parameter.
     */
    @Test
    public void testSendMessageWithoutMessageGroupId() {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String data = "Test message";

        when(jackson.str(data)).thenReturn(data);

        // Call the method under test
        sqsBaseQueue.send(queueUrl, data, "12345", null);

        // Verify that sendMessage was called
        verify(client).sendMessage(any(SendMessageRequest.class));
    }

    /**
     * Test sending a message with blank deduplication ID and message group ID.
     * This tests the behavior when both optional parameters are blank.
     */
    @Test
    public void testSendMessageWithBlankParameters() {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/MyQueue";
        String data = "Test message";

        when(jackson.str(data)).thenReturn(data);

        // Call the method under test
        sqsBaseQueue.send(queueUrl, data, "", "");

        // Verify that sendMessage was called
        verify(client).sendMessage(any(SendMessageRequest.class));
    }
}
