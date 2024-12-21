package qiangyt.fraud_detection.app.service;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.alert.Alerter;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionRequestQueue;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionApi;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DetectionServiceTest {

    @Mock
    private DetectionRequestQueue requestQueue;

    @Mock
    private DetectionEngine engine;

    @Mock
    private Jackson jackson;

    @Mock
    private Alerter alertor;

    @InjectMocks
    private DetectionService detectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for submitting a detection request successfully.
     * This is the happy path where the request is processed without any issues.
     */
    @Test
    public void testSubmitDetectionRequest() {
        DetectionReq req = mock(DetectionReq.class);
        DetectionReqEntity entity = mock(DetectionReqEntity.class);
        when(req.toEntity()).thenReturn(entity);

        DetectionReqEntity result = detectionService.submit(req);

        // Verify that the request queue sends the entity
        verify(requestQueue).send(entity);
        assertEquals(entity, result);
    }

    /**
     * Test case for detecting fraud and sending an alert when fraud is detected.
     * This tests the positive case where the detection engine identifies fraud.
     */
    @Test
    public void testDetectThenAlert_FraudDetected() {
        DetectionReqEntity entity = mock(DetectionReqEntity.class);
        DetectionResult result = mock(DetectionResult.class);
        when(result.getCategory().yes).thenReturn(true);
        when(engine.detect(entity)).thenReturn(result);
        when(jackson.str(result)).thenReturn("Fraud detected!");

        CompletableFuture<DetectionResult> futureResult = detectionService.detectThenAlert(entity);

        // Wait for the CompletableFuture to complete and get the result
        DetectionResult finalResult = futureResult.join();

        // Verify that the alertor sends an alert
        verify(alertor).send(finalResult);
        assertEquals(result, finalResult);
    }

    /**
     * Test case for detecting fraud without sending an alert when no fraud is detected.
     * This tests the positive case where the detection engine does not identify fraud.
     */
    @Test
    public void testDetectThenAlert_NoFraudDetected() {
        DetectionReqEntity entity = mock(DetectionReqEntity.class);
        DetectionResult result = mock(DetectionResult.class);
        when(result.getCategory().yes).thenReturn(false);
        when(engine.detect(entity)).thenReturn(result);
        when(jackson.str(result)).thenReturn("No fraud detected!");

        CompletableFuture<DetectionResult> futureResult = detectionService.detectThenAlert(entity);

        // Wait for the CompletableFuture to complete and get the result
        DetectionResult finalResult = futureResult.join();

        // Verify that the alertor does not send an alert
        verify(alertor, never()).send(finalResult);
        assertEquals(result, finalResult);
    }

    /**
     * Test case for detecting fraud with a null request entity.
     * This tests the negative case where the input is invalid (null).
     */
    @Test
    public void testDetect_NullEntity() {
        assertThrows(NullPointerException.class, () -> {
            detectionService.detect(null);
        });
    }

    /**
     * Test case for submitting a null detection request.
     * This tests the negative case where the input is invalid (null).
     */
    @Test
    public void testSubmit_NullRequest() {
        assertThrows(NullPointerException.class, () -> {
            detectionService.submit(null);
        });
    }

    /**
     * Test case for detecting fraud with an invalid detection request entity.
     * This tests a corner case where the detection engine returns a null result.
     */
    @Test
    public void testDetect_InvalidEntity() {
        DetectionReqEntity entity = mock(DetectionReqEntity.class);
        when(engine.detect(entity)).thenReturn(null);

        DetectionResult result = detectionService.detect(entity);

        // Verify that the result is null
        assertNull(result);
    }
}
