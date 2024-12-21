package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExceptionHelper.
 */
public class ExceptionHelperTest {

    /**
     * Test case for getRootCause with a simple exception.
     * This tests the happy path where the root cause is the exception itself.
     */
    @Test
    public void testGetRootCause_SimpleException() {
        // Arrange
        Exception exception = new Exception("Simple exception");

        // Act
        Throwable result = ExceptionHelper.getRootCause(exception);

        // Assert
        assertEquals(exception, result);
    }

    /**
     * Test case for getRootCause with nested exceptions.
     * This tests the positive case where the root cause is a nested exception.
     */
    @Test
    public void testGetRootCause_NestedExceptions() {
        // Arrange
        Exception innerException = new Exception("Inner exception");
        Exception outerException = new Exception("Outer exception", innerException);

        // Act
        Throwable result = ExceptionHelper.getRootCause(outerException);

        // Assert
        assertEquals(innerException, result);
    }

    /**
     * Test case for getRootCause with InvocationTargetException.
     * This tests the positive case where the root cause is wrapped in an InvocationTargetException.
     */
    @Test
    public void testGetRootCause_InvocationTargetException() {
        // Arrange
        Exception innerException = new Exception("Inner exception");
        InvocationTargetException invocationException = new InvocationTargetException(innerException, "Invocation exception");

        // Act
        Throwable result = ExceptionHelper.getRootCause(invocationException);

        // Assert
        assertEquals(innerException, result);
    }

    /**
     * Test case for topElement with a simple exception.
     * This tests the happy path where the top stack trace element is retrieved correctly.
     */
    @Test
    public void testTopElement_SimpleException() {
        // Arrange
        Exception exception = new Exception("Simple exception");

        // Act
        StackTraceElement result = ExceptionHelper.topElement(exception);

        // Assert
        assertNotNull(result);
    }

    /**
     * Test case for topElement with no stack trace.
     * This tests the corner case where the exception has no stack trace.
     */
    @Test
    public void testTopElement_NoStackTrace() {
        // Arrange
        Throwable throwable = new Throwable();

        // Act
        StackTraceElement result = ExceptionHelper.topElement(throwable);

        // Assert
        assertNull(result);
    }

    /**
     * Test case for topElement with a null throwable.
     * This tests the negative case where a null throwable is passed.
     */
    @Test
    public void testTopElement_NullThrowable() {
        // Act
        StackTraceElement result = ExceptionHelper.topElement(null);

        // Assert
        assertNull(result);
    }
}
