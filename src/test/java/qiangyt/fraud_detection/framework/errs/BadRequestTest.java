package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the BadRequest class.
 */
public class BadRequestTest {

    /**
     * Test the constructor with error code, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCodeAndMessageFormat() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;
        String messageFormat = "Invalid request for user %s";
        BadRequest badRequest = new BadRequest(code, messageFormat, "user123");

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertEquals("Invalid request for user user123", badRequest.getMessage());
    }

    /**
     * Test the constructor with error code and message.
     * This is a positive test case.
     */
    @Test
    public void testConstructorWithCodeAndMessage() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;
        String message = "Invalid request";

        BadRequest badRequest = new BadRequest(code, message);

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertEquals(message, badRequest.getMessage());
    }

    /**
     * Test the constructor with error code only.
     * This is a positive test case.
     */
    @Test
    public void testConstructorWithCodeOnly() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;

        BadRequest badRequest = new BadRequest(code);

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertNull(badRequest.getMessage());
    }

    /**
     * Test the constructor with error code, cause, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCodeCauseMessageFormat() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;
        Throwable cause = new RuntimeException("Cause of the error");
        String messageFormat = "Error occurred for user %s";
        BadRequest badRequest = new BadRequest(code, cause, messageFormat, "user123");

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertEquals("Error occurred for user user123", badRequest.getMessage());
        assertEquals(cause, badRequest.getCause());
    }

    /**
     * Test the constructor with error code, cause, and message.
     * This is a positive test case.
     */
    @Test
    public void testConstructorWithCodeCauseMessage() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;
        Throwable cause = new RuntimeException("Cause of the error");
        String message = "Invalid request";

        BadRequest badRequest = new BadRequest(code, cause, message);

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertEquals(message, badRequest.getMessage());
        assertEquals(cause, badRequest.getCause());
    }

    /**
     * Test the constructor with error code and cause.
     * This is a corner case test case.
     */
    @Test
    public void testConstructorWithCodeAndCause() {
        ErrorCode code = ErrorCode.INVALID_REQUEST;
        Throwable cause = new RuntimeException("Cause of the error");

        BadRequest badRequest = new BadRequest(code, cause);

        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(code, badRequest.getCode());
        assertNull(badRequest.getMessage());
        assertEquals(cause, badRequest.getCause());
    }
}
