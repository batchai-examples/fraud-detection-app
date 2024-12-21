package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for MethodNotAllowed class.
 */
public class MethodNotAllowedTest {

    /**
     * Test the constructor with message format and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithMessageFormatAndParams() {
        MethodNotAllowed exception = new MethodNotAllowed("Error occurred: %s", "Invalid Method");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals("Error occurred: Invalid Method", exception.getMessage());
    }

    /**
     * Test the constructor with a single message.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithMessage() {
        MethodNotAllowed exception = new MethodNotAllowed("Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals("Method not allowed", exception.getMessage());
    }

    /**
     * Test the default constructor.
     * This is a happy path test case.
     */
    @Test
    public void testDefaultConstructor() {
        MethodNotAllowed exception = new MethodNotAllowed();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(null, exception.getMessage());
    }

    /**
     * Test the constructor with cause, message format, and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCauseMessageFormatAndParams() {
        Throwable cause = new RuntimeException("Cause of error");
        MethodNotAllowed exception = new MethodNotAllowed(cause, "Error occurred: %s", "Invalid Method");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals("Error occurred: Invalid Method", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    /**
     * Test the constructor with cause and message.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        Throwable cause = new RuntimeException("Cause of error");
        MethodNotAllowed exception = new MethodNotAllowed(cause, "Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals("Method not allowed", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    /**
     * Test the constructor with cause only.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause of error");
        MethodNotAllowed exception = new MethodNotAllowed(cause);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(null, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
