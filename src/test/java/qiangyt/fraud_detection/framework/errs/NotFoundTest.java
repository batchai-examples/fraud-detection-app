package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the NotFound exception.
 */
public class NotFoundTest {

    /**
     * Test the constructor with error code, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCodeMessageFormatAndParams() {
        ErrorCode code = ErrorCode.NOT_FOUND; // Assume ErrorCode is an enum
        String messageFormat = "Resource not found: %s";
        String resourceId = "12345";
        
        NotFound exception = new NotFound(code, messageFormat, resourceId);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
        assertEquals(String.format(messageFormat, resourceId), exception.getMessage());
    }

    /**
     * Test the constructor with error code and message.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCodeAndMessage() {
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "Resource not found";
        
        NotFound exception = new NotFound(code, message);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with error code only.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCode() {
        ErrorCode code = ErrorCode.NOT_FOUND;
        
        NotFound exception = new NotFound(code);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
    }

    /**
     * Test the constructor with error code, cause, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCodeCauseMessageFormatAndParams() {
        ErrorCode code = ErrorCode.NOT_FOUND;
        Throwable cause = new RuntimeException("Cause");
        String messageFormat = "Resource not found: %s";
        String resourceId = "12345";
        
        NotFound exception = new NotFound(code, cause, messageFormat, resourceId);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
        assertEquals(cause, exception.getCause());
        assertEquals(String.format(messageFormat, resourceId), exception.getMessage());
    }

    /**
     * Test the constructor with error code, cause, and message.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCodeCauseAndMessage() {
        ErrorCode code = ErrorCode.NOT_FOUND;
        Throwable cause = new RuntimeException("Cause");
        String message = "Resource not found";
        
        NotFound exception = new NotFound(code, cause, message);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
        assertEquals(cause, exception.getCause());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with error code and cause.
     * This is a happy path test case.
     */
    @Test
    public void testNotFoundWithCodeAndCause() {
        ErrorCode code = ErrorCode.NOT_FOUND;
        Throwable cause = new RuntimeException("Cause");
        
        NotFound exception = new NotFound(code, cause);
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(code, exception.getCode());
        assertEquals(cause, exception.getCause());
    }
}
