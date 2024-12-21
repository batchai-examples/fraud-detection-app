package qiangyt.fraud_detection.framework.errs;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for ErrorAdvice to ensure proper handling of various exceptions.
 */
public class ErrorAdviceTest {

    private ErrorAdvice errorAdvice;
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        errorAdvice = new ErrorAdvice();
        request = Mockito.mock(HttpServletRequest.class);
    }

    /**
     * Test handling of a generic exception.
     */
    @Test
    public void testHandleError_GenericException() {
        Throwable ex = new Exception("Generic error");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of MethodArgumentNotValidException.
     */
    @Test
    public void testHandleError_MethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of ConstraintViolationException.
     */
    @Test
    public void testHandleError_ConstraintViolationException() {
        ConstraintViolationException ex = Mockito.mock(ConstraintViolationException.class);
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of HttpMessageNotReadableException.
     */
    @Test
    public void testHandleError_HttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message not readable");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of HttpMediaTypeNotSupportedException.
     */
    @Test
    public void testHandleError_HttpMediaTypeNotSupportedException() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("Media type not supported");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of HttpRequestMethodNotSupportedException.
     */
    @Test
    public void testHandleError_HttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("Method not supported");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of NoHandlerFoundException.
     */
    @Test
    public void testHandleError_NoHandlerFoundException() {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/test", null);
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of AsyncRequestTimeoutException.
     */
    @Test
    public void testHandleError_AsyncRequestTimeoutException() {
        AsyncRequestTimeoutException ex = new AsyncRequestTimeoutException("Request timeout");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.REQUEST_TIMEOUT, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of IllegalArgumentException.
     */
    @Test
    public void testHandleError_IllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that the response contains the expected error message
    }

    /**
     * Test handling of JacksonException.
     */
    @Test
    public void testHandleError_JacksonException() {
        JacksonException ex = new JacksonException("Jackson error");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verify that the response contains the expected error message
    }
}
