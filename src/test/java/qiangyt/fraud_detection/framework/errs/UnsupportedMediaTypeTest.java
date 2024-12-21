package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the UnsupportedMediaType exception class.
 */
public class UnsupportedMediaTypeTest {

    /**
     * Test case for creating UnsupportedMediaType with a message format and parameters.
     * Expected: Exception should be created with the correct message.
     */
    @Test
    public void testUnsupportedMediaTypeWithMessageFormat() {
        String messageFormat = "Unsupported media type: %s";
        String mediaType = "application/xml";
        UnsupportedMediaType exception = new UnsupportedMediaType(messageFormat, mediaType);
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertEquals(String.format(messageFormat, mediaType), exception.getMessage());
    }

    /**
     * Test case for creating UnsupportedMediaType with a simple message.
     * Expected: Exception should be created with the correct message.
     */
    @Test
    public void testUnsupportedMediaTypeWithMessage() {
        String message = "Unsupported media type";
        UnsupportedMediaType exception = new UnsupportedMediaType(message);
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test case for creating UnsupportedMediaType with no message.
     * Expected: Exception should be created without a message.
     */
    @Test
    public void testUnsupportedMediaTypeNoMessage() {
        UnsupportedMediaType exception = new UnsupportedMediaType();
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertNull(exception.getMessage());
    }

    /**
     * Test case for creating UnsupportedMediaType with a cause and message format.
     * Expected: Exception should be created with the correct cause and message.
     */
    @Test
    public void testUnsupportedMediaTypeWithCauseAndMessageFormat() {
        Throwable cause = new RuntimeException("Cause of the error");
        String messageFormat = "Error due to unsupported media type: %s";
        String mediaType = "application/xml";
        UnsupportedMediaType exception = new UnsupportedMediaType(cause, messageFormat, mediaType);
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertEquals(String.format(messageFormat, mediaType), exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    /**
     * Test case for creating UnsupportedMediaType with a cause and simple message.
     * Expected: Exception should be created with the correct cause and message.
     */
    @Test
    public void testUnsupportedMediaTypeWithCauseAndMessage() {
        Throwable cause = new RuntimeException("Cause of the error");
        String message = "Unsupported media type";
        UnsupportedMediaType exception = new UnsupportedMediaType(cause, message);
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    /**
     * Test case for creating UnsupportedMediaType with a cause only.
     * Expected: Exception should be created with the correct cause.
     */
    @Test
    public void testUnsupportedMediaTypeWithCause() {
        Throwable cause = new RuntimeException("Cause of the error");
        UnsupportedMediaType exception = new UnsupportedMediaType(cause);
        
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getHttpStatus());
        assertEquals(cause, exception.getCause());
        assertNull(exception.getMessage());
    }
}
