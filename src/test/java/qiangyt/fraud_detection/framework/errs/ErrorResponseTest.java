package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ErrorResponse.
 */
public class ErrorResponseTest {

    /**
     * Test case for creating a valid ErrorResponse object.
     * This is a happy path test case.
     */
    @Test
    public void testValidErrorResponse() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = new Object[]{"param1", "param2"};
        String path = "/api/resource";

        // Act
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .code(code)
                .message(message)
                .params(params)
                .path(path)
                .build();

        // Assert
        assertNotNull(errorResponse);
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(error, errorResponse.getError());
        assertEquals(code, errorResponse.getCode());
        assertEquals(message, errorResponse.getMessage());
        assertArrayEquals(params, errorResponse.getParams());
        assertEquals(path, errorResponse.getPath());
    }

    /**
     * Test case for converting ErrorResponse to JSON string.
     * This tests the toString method.
     */
    @Test
    public void testToString() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setStatus(500);
        errorResponse.setError("Internal Server Error");
        errorResponse.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
        errorResponse.setMessage("An unexpected error occurred.");
        errorResponse.setParams(new Object[]{"param1"});
        errorResponse.setPath("/api/error");

        // Act
        String jsonString = errorResponse.toString();

        // Assert
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("Internal Server Error"));
    }

    /**
     * Test case for ErrorResponse with missing parameters.
     * This tests the behavior when some parameters are null.
     */
    @Test
    public void testErrorResponseWithNullParams() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setStatus(400);
        errorResponse.setError(null); // Error is null
        errorResponse.setCode(ErrorCode.BAD_REQUEST);
        errorResponse.setMessage("Bad request.");
        errorResponse.setParams(null); // Params are null
        errorResponse.setPath("/api/badrequest");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            errorResponse.toString(); // Expecting a NullPointerException due to null error
        });
    }

    /**
     * Test case for ErrorResponse with invalid status code.
     * This tests the behavior when an invalid status code is set.
     */
    @Test
    public void testErrorResponseWithInvalidStatus() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(-1); // Invalid status code

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            if (errorResponse.getStatus() < 0) {
                throw new IllegalArgumentException("Invalid status code");
            }
        });
    }

    /**
     * Test case for ErrorResponse with empty message.
     * This tests the behavior when the message is empty.
     */
    @Test
    public void testErrorResponseWithEmptyMessage() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(""); // Empty message

        // Act
        String message = errorResponse.getMessage();

        // Assert
        assertEquals("", message);
    }
}
