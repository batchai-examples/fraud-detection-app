package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RequestTimeout exception.
 */
public class RequestTimeoutTest {

    /**
     * Test case for the constructor with formatted message and parameters.
     * This tests the happy path where the message is formatted correctly.
     */
    @Test
    public void testConstructorWithFormattedMessage() {
        String messageFormat = "Request timed out after %d seconds";
        int timeout = 30;
        RequestTimeout exception = new RequestTimeout(messageFormat, timeout);
        
        // Verify that the exception message is formatted correctly
        assertEquals("Request timed out after 30 seconds", exception.getMessage());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }

    /**
     * Test case for the constructor with a simple message.
     * This tests the positive case where a message is provided.
     */
    @Test
    public void testConstructorWithMessage() {
        String message = "Request has timed out";
        RequestTimeout exception = new RequestTimeout(message);
        
        // Verify that the exception message is as expected
        assertEquals(message, exception.getMessage());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }

    /**
     * Test case for the default constructor.
     * This tests the case where no message is provided.
     */
    @Test
    public void testDefaultConstructor() {
        RequestTimeout exception = new RequestTimeout();
        
        // Verify that the exception message is null
        assertNull(exception.getMessage());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }

    /**
     * Test case for the constructor with cause and formatted message.
     * This tests the case where a cause is provided along with a formatted message.
     */
    @Test
    public void testConstructorWithCauseAndFormattedMessage() {
        Throwable cause = new RuntimeException("Timeout error");
        String messageFormat = "Request timed out after %d seconds";
        int timeout = 15;
        RequestTimeout exception = new RequestTimeout(cause, messageFormat, timeout);
        
        // Verify that the exception message is formatted correctly and cause is set
        assertEquals("Request timed out after 15 seconds", exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }

    /**
     * Test case for the constructor with cause and message.
     * This tests the case where a cause is provided along with a simple message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        Throwable cause = new RuntimeException("Timeout error");
        String message = "Request has timed out";
        RequestTimeout exception = new RequestTimeout(cause, message);
        
        // Verify that the exception message is as expected and cause is set
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }

    /**
     * Test case for the constructor with cause only.
     * This tests the case where only a cause is provided.
     */
    @Test
    public void testConstructorWithCauseOnly() {
        Throwable cause = new RuntimeException("Timeout error");
        RequestTimeout exception = new RequestTimeout(cause);
        
        // Verify that the exception message is null and cause is set
        assertNull(exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
    }
}
