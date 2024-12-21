package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RemoteError.
 * This class contains test cases to validate the behavior of the RemoteError class.
 */
public class RemoteErrorTest {

    /**
     * Test case for successful creation of RemoteError with valid ErrorResponse.
     * This is a happy path test case.
     */
    @Test
    public void testRemoteErrorCreation_ValidResponse() {
        // Arrange
        ErrorResponse response = new ErrorResponse("404", "Not Found", null);
        
        // Act
        RemoteError remoteError = new RemoteError(response);
        
        // Assert
        assertNotNull(remoteError);
        assertEquals("404", remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNull(remoteError.getParams());
    }

    /**
     * Test case for RemoteError creation with an ErrorResponse having empty code.
     * This is a positive case to check handling of empty code.
     */
    @Test
    public void testRemoteErrorCreation_EmptyCode() {
        // Arrange
        ErrorResponse response = new ErrorResponse("", "Empty Code", null);
        
        // Act
        RemoteError remoteError = new RemoteError(response);
        
        // Assert
        assertNotNull(remoteError);
        assertEquals("", remoteError.getCode());
        assertEquals("Empty Code", remoteError.getMessage());
        assertNull(remoteError.getParams());
    }

    /**
     * Test case for RemoteError creation with an ErrorResponse having null message.
     * This is a positive case to check handling of null message.
     */
    @Test
    public void testRemoteErrorCreation_NullMessage() {
        // Arrange
        ErrorResponse response = new ErrorResponse("500", null, null);
        
        // Act
        RemoteError remoteError = new RemoteError(response);
        
        // Assert
        assertNotNull(remoteError);
        assertEquals("500", remoteError.getCode());
        assertNull(remoteError.getMessage());
        assertNull(remoteError.getParams());
    }

    /**
     * Test case for RemoteError creation with an ErrorResponse having null params.
     * This is a corner case to check handling of null params.
     */
    @Test
    public void testRemoteErrorCreation_NullParams() {
        // Arrange
        ErrorResponse response = new ErrorResponse("400", "Bad Request", null);
        
        // Act
        RemoteError remoteError = new RemoteError(response);
        
        // Assert
        assertNotNull(remoteError);
        assertEquals("400", remoteError.getCode());
        assertEquals("Bad Request", remoteError.getMessage());
        assertNull(remoteError.getParams());
    }

    /**
     * Test case for RemoteError creation with an ErrorResponse having invalid code format.
     * This is a negative case to check handling of invalid code format.
     */
    @Test
    public void testRemoteErrorCreation_InvalidCodeFormat() {
        // Arrange
        ErrorResponse response = new ErrorResponse("INVALID_CODE", "Invalid Code Format", null);
        
        // Act
        RemoteError remoteError = new RemoteError(response);
        
        // Assert
        assertNotNull(remoteError);
        assertEquals("INVALID_CODE", remoteError.getCode());
        assertEquals("Invalid Code Format", remoteError.getMessage());
        assertNull(remoteError.getParams());
    }
}
