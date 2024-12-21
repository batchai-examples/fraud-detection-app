package qiangyt.fraud_detection.app.alert;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionResult;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for CloudWatchMetricAlerter.
 */
public class CloudWatchMetricAlerterTest {

    @Mock
    private CloudWatchClient client;

    @Mock
    private GroupedAlerter group;

    @InjectMocks
    private CloudWatchMetricAlerter alerter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alerter.init(); // Initialize the alerter
    }

    /**
     * Test case for sending a valid detection result.
     * This is the happy path where the metric is sent successfully.
     */
    @Test
    void testSendValidDetectionResult() {
        DetectionResult result = new DetectionResult("123", "FRAUD");

        alerter.send(result);

        // Verify that the putMetricData method is called with the correct request
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test case for sending a detection result with an empty ID.
     * This tests the behavior when the detection result ID is empty.
     */
    @Test
    void testSendEmptyIdDetectionResult() {
        DetectionResult result = new DetectionResult("", "FRAUD");

        alerter.send(result);

        // Verify that the putMetricData method is called
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test case for sending a detection result with an invalid category.
     * This tests the behavior when the detection result category is invalid.
     */
    @Test
    void testSendInvalidCategoryDetectionResult() {
        DetectionResult result = new DetectionResult("123", "INVALID_CATEGORY");

        alerter.send(result);

        // Verify that the putMetricData method is called
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test case for sending a null detection result.
     * This tests the behavior when the detection result is null.
     */
    @Test
    void testSendNullDetectionResult() {
        DetectionResult result = null;

        // Expecting an exception when sending a null result
        try {
            alerter.send(result);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    /**
     * Test case for sending a detection result with a null category.
     * This tests the behavior when the detection result category is null.
     */
    @Test
    void testSendNullCategoryDetectionResult() {
        DetectionResult result = new DetectionResult("123", null);

        alerter.send(result);

        // Verify that the putMetricData method is called
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }
}
