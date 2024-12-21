package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the BaseError class.
 */
public class BaseErrorTest {

    /**
     * Test the constructor with status, code, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testBaseErrorWithFormat() {
        BaseError error = new BaseError(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST, "Error occurred: %s", "Invalid input");
        
        // Verify that the error message is formatted correctly
        assertEquals("Error occurred: Invalid input", error.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
        assertEquals(ErrorCode.INVALID_REQUEST, error.getCode());
        assertArrayEquals(new Object[]{"Invalid input"}, error.getParams());
    }

    /**
     * Test the constructor with status, code, and simple message.
     * This is a positive case.
     */
    @Test
    public void testBaseErrorWithSimpleMessage() {
        BaseError error = new BaseError(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "Resource not found");
        
        // Verify that the error message is set correctly
        assertEquals("Resource not found", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
        assertEquals(ErrorCode.NOT_FOUND, error.getCode());
        assertArrayEquals(BaseError.NO_PARAMS, error.getParams());
    }

    /**
     * Test the constructor with status and code only.
     * This is a corner case where only the status and code are provided.
     */
    @Test
    public void testBaseErrorWithStatusAndCode() {
        BaseError error = new BaseError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE);
        
        // Verify that the default message is used
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), error.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
        assertEquals(ErrorCode.NONE, error.getCode());
        assertArrayEquals(BaseError.NO_PARAMS, error.getParams());
    }

    /**
     * Test the constructor with a cause, status, code, message format, and parameters.
     * This is a happy path test case with a cascading exception.
     */
    @Test
    public void testBaseErrorWithCauseAndFormat() {
        Throwable cause = new RuntimeException("Underlying cause");
        BaseError error = new BaseError(HttpStatus.FORBIDDEN, ErrorCode.ACCESS_DENIED, cause, "Access denied: %s", "User not authorized");
        
        // Verify that the error message is formatted correctly and includes the cause
        assertEquals("Access denied: User not authorized", error.getMessage());
        assertEquals(cause, error.getCause());
        assertEquals(HttpStatus.FORBIDDEN, error.getStatus());
        assertEquals(ErrorCode.ACCESS_DENIED, error.getCode());
        assertArrayEquals(new Object[]{"User not authorized"}, error.getParams());
    }

    /**
     * Test the constructor with a cause, status, code, and simple message.
     * This is a positive case with a cascading exception.
     */
    @Test
    public void testBaseErrorWithCauseAndSimpleMessage() {
        Throwable cause = new RuntimeException("Underlying cause");
        BaseError error = new BaseError(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, cause, "Unauthorized access");
        
        // Verify that the error message is set correctly and includes the cause
        assertEquals("Unauthorized access", error.getMessage());
        assertEquals(cause, error.getCause());
        assertEquals(HttpStatus.UNAUTHORIZED, error.getStatus());
        assertEquals(ErrorCode.UNAUTHORIZED, error.getCode());
        assertArrayEquals(BaseError.NO_PARAMS, error.getParams());
    }

    /**
     * Test the constructor with a cause, status, and code only.
     * This is a corner case where only the cause, status, and code are provided.
     */
    @Test
    public void testBaseErrorWithCauseAndStatusAndCode() {
        Throwable cause = new RuntimeException("Underlying cause");
        BaseError error = new BaseError(HttpStatus.BAD_GATEWAY, ErrorCode.NONE, cause);
        
        // Verify that the default message is used and includes the cause
        assertEquals(HttpStatus.BAD_GATEWAY.getReasonPhrase(), error.getMessage());
        assertEquals(cause, error.getCause());
        assertEquals(HttpStatus.BAD_GATEWAY, error.getStatus());
        assertEquals(ErrorCode.NONE, error.getCode());
        assertArrayEquals(BaseError.NO_PARAMS, error.getParams());
    }
}
