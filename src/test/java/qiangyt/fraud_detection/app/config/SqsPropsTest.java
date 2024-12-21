package qiangyt.fraud_detection.app.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SqsProps.
 * This class contains test cases to validate the properties of the SqsProps class.
 */
public class SqsPropsTest {

    /**
     * Test the default values of SqsProps.
     * This test checks if the default values are set correctly when an instance of SqsProps is created.
     */
    @Test
    public void testDefaultValues() {
        SqsProps sqsProps = new SqsProps();
        
        // Assert default thread pool size
        assertEquals(1, sqsProps.getThreadPoolSize(), "Default thread pool size should be 1");
        
        // Assert default batch size
        assertEquals(10, sqsProps.getBatchSize(), "Default batch size should be 10");
        
        // Assert default timeout
        assertEquals(20, sqsProps.getTimeout(), "Default timeout should be 20 seconds");
        
        // Assert default URLs are null since environment variables are not set
        assertNull(sqsProps.getDetectQueueUrl(), "Detect queue URL should be null if not set");
        assertNull(sqsProps.getDetectDeadLetterQueueUrl(), "Dead letter queue URL should be null if not set");
        assertNull(sqsProps.getAlertQueueUrl(), "Alert queue URL should be null if not set");
    }

    /**
     * Test setting and getting thread pool size.
     * This test checks if the thread pool size can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetThreadPoolSize() {
        SqsProps sqsProps = new SqsProps();
        sqsProps.setThreadPoolSize(5);
        
        // Assert thread pool size is set correctly
        assertEquals(5, sqsProps.getThreadPoolSize(), "Thread pool size should be 5");
    }

    /**
     * Test setting and getting batch size.
     * This test checks if the batch size can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetBatchSize() {
        SqsProps sqsProps = new SqsProps();
        sqsProps.setBatchSize(15);
        
        // Assert batch size is set correctly
        assertEquals(15, sqsProps.getBatchSize(), "Batch size should be 15");
    }

    /**
     * Test setting and getting timeout.
     * This test checks if the timeout can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetTimeout() {
        SqsProps sqsProps = new SqsProps();
        sqsProps.setTimeout(30);
        
        // Assert timeout is set correctly
        assertEquals(30, sqsProps.getTimeout(), "Timeout should be 30 seconds");
    }

    /**
     * Test setting and getting detect queue URL.
     * This test checks if the detect queue URL can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetDetectQueueUrl() {
        SqsProps sqsProps = new SqsProps();
        String url = "https://sqs.example.com/detect";
        sqsProps.setDetectQueueUrl(url);
        
        // Assert detect queue URL is set correctly
        assertEquals(url, sqsProps.getDetectQueueUrl(), "Detect queue URL should match the set value");
    }

    /**
     * Test setting and getting alert queue URL.
     * This test checks if the alert queue URL can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetAlertQueueUrl() {
        SqsProps sqsProps = new SqsProps();
        String url = "https://sqs.example.com/alert.fifo";
        sqsProps.setAlertQueueUrl(url);
        
        // Assert alert queue URL is set correctly
        assertEquals(url, sqsProps.getAlertQueueUrl(), "Alert queue URL should match the set value");
    }
}
