package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the NotAcceptable class.
 */
public class NotAcceptableTest {

    /**
     * Test the constructor with a formatted message and parameters.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithFormattedMessage() {
        NotAcceptable error = new NotAcceptable("Error: %s", "Invalid format");
        assertNotNull(error);
        assertEquals("Error: Invalid format", error.getMessage());
    }

    /**
     * Test the constructor with a simple message.
     * This is a positive case.
     */
    @Test
    public void testConstructorWithMessage() {
        NotAcceptable error = new NotAcceptable("Simple error message");
        assertNotNull(error);
        assertEquals("Simple error message", error.getMessage());
    }

    /**
     * Test the default constructor.
     * This is a corner case where no message is provided.
     */
    @Test
    public void testDefaultConstructor() {
        NotAcceptable error = new NotAcceptable();
        assertNotNull(error);
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with a cause and a formatted message.
     * This is a happy path test case.
     */
    @Test
    public void testConstructorWithCauseAndFormattedMessage() {
        Throwable cause = new RuntimeException("Cause of the error");
        NotAcceptable error = new NotAcceptable(cause, "Error caused by: %s", cause.getMessage());
        assertNotNull(error);
        assertEquals("Error caused by: Cause of the error", error.getMessage());
        assertEquals(cause, error.getCause());
    }

    /**
     * Test the constructor with a cause and a simple message.
     * This is a positive case.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        Throwable cause = new RuntimeException("Cause of the error");
        NotAcceptable error = new NotAcceptable(cause, "Error occurred");
        assertNotNull(error);
        assertEquals("Error occurred", error.getMessage());
        assertEquals(cause, error.getCause());
    }

    /**
     * Test the constructor with a cause only.
     * This is a corner case where only the cause is provided.
     */
    @Test
    public void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause of the error");
        NotAcceptable error = new NotAcceptable(cause);
        assertNotNull(error);
        assertEquals(cause, error.getCause());
        assertNull(error.getMessage());
    }
}
