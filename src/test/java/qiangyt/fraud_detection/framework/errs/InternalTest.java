package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Internal class.
 */
public class InternalTest {

    /**
     * Test the constructor with message format and parameters.
     * 
     * This test verifies that the Internal error is created correctly
     * when provided with a message format and parameters.
     */
    @Test
    public void testConstructorWithMessageFormatAndParams() {
        Internal internalError = new Internal("Error occurred: %s", "Database connection failed");
        assertNotNull(internalError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, internalError.getStatus());
        assertEquals("Error occurred: Database connection failed", internalError.getMessage());
    }

    /**
     * Test the constructor with a message.
     * 
     * This test checks that the Internal error is created correctly
     * when only a message is provided.
     */
    @Test
    public void testConstructorWithMessage() {
        Internal internalError = new Internal("An unexpected error occurred");
        assertNotNull(internalError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, internalError.getStatus());
        assertEquals("An unexpected error occurred", internalError.getMessage());
    }

    /**
     * Test the constructor with cause, message format, and parameters.
     * 
     * This test ensures that the Internal error is created correctly
     * when a cause, message format, and parameters are provided.
     */
    @Test
    public void testConstructorWithCauseMessageFormatAndParams() {
        Throwable cause = new RuntimeException("Database error");
        Internal internalError = new Internal(cause, "Error occurred: %s", "Database connection failed");
        assertNotNull(internalError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, internalError.getStatus());
        assertEquals("Error occurred: Database connection failed", internalError.getMessage());
        assertEquals(cause, internalError.getCause());
    }

    /**
     * Test the constructor with cause and message.
     * 
     * This test verifies that the Internal error is created correctly
     * when a cause and a message are provided.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        Throwable cause = new RuntimeException("Database error");
        Internal internalError = new Internal(cause, "An unexpected error occurred");
        assertNotNull(internalError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, internalError.getStatus());
        assertEquals("An unexpected error occurred", internalError.getMessage());
        assertEquals(cause, internalError.getCause());
    }

    /**
     * Test the constructor with only cause.
     * 
     * This test checks that the Internal error is created correctly
     * when only a cause is provided.
     */
    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Database error");
        Internal internalError = new Internal(cause);
        assertNotNull(internalError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, internalError.getStatus());
        assertEquals(cause, internalError.getCause());
    }

    /**
     * Test the toResponse method.
     * 
     * This test verifies that the toResponse method returns the correct
     * ErrorResponse object with the expected properties.
     */
    @Test
    public void testToResponse() {
        Internal internalError = new Internal("An unexpected error occurred");
        ErrorResponse response = internalError.toResponse("/api/test");
        assertNotNull(response);
        assertEquals("/api/test", response.getPath());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Internal Server Error", response.getError());
        assertEquals(ErrorCode.NONE, response.getCode());
        assertEquals("", response.getMessage());
    }
}
