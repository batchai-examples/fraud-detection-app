package qiangyt.fraud_detection.app.alert;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.queue.SqsBaseQueue;
import qiangyt.fraud_detection.sdk.DetectionResult;

import static org.mockito.Mockito.*;

public class SqsAlerterTest {

    @Mock
    private GroupedAlerter group;

    @InjectMocks
    private SqsAlerter sqsAlerter;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the initialization of SqsAlerter.
     * This test verifies that the SqsAlerter is registered with the GroupedAlerter upon initialization.
     */
    @Test
    void testInit() {
        // Act
        sqsAlerter.init();
        
        // Assert
        verify(group).registerAlerter(sqsAlerter);
    }

    /**
     * Test sending a valid DetectionResult.
     * This test checks that the send method is called with the correct parameters when a valid DetectionResult is provided.
     */
    @Test
    void testSendValidDetectionResult() {
        // Arrange
        DetectionResult result = mock(DetectionResult.class);
        when(result.getEntity()).thenReturn(mock(Entity.class));
        when(result.getEntity().getId()).thenReturn("12345");
        sqsAlerter.getProps().setAlertQueueUrl("http://example.com/queue");

        // Act
        sqsAlerter.send(result);
        
        // Assert
        verify(sqsAlerter).send("http://example.com/queue", result, "12345", "alert");
    }

    /**
     * Test sending a null DetectionResult.
     * This test checks that the send method handles a null DetectionResult gracefully.
     */
    @Test
    void testSendNullDetectionResult() {
        // Act
        sqsAlerter.send(null);
        
        // Assert
        // Verify that send method is not called with null
        verify(sqsAlerter, never()).send(anyString(), any(), anyString(), anyString());
    }

    /**
     * Test sending a DetectionResult with a null entity.
     * This test checks that the send method handles a DetectionResult with a null entity gracefully.
     */
    @Test
    void testSendDetectionResultWithNullEntity() {
        // Arrange
        DetectionResult result = mock(DetectionResult.class);
        when(result.getEntity()).thenReturn(null);
        
        // Act
        sqsAlerter.send(result);
        
        // Assert
        // Verify that send method is not called with null entity
        verify(sqsAlerter, never()).send(anyString(), any(), anyString(), anyString());
    }

    /**
     * Test sending a DetectionResult with an empty ID.
     * This test checks that the send method handles a DetectionResult with an empty ID gracefully.
     */
    @Test
    void testSendDetectionResultWithEmptyId() {
        // Arrange
        DetectionResult result = mock(DetectionResult.class);
        when(result.getEntity()).thenReturn(mock(Entity.class));
        when(result.getEntity().getId()).thenReturn("");
        sqsAlerter.getProps().setAlertQueueUrl("http://example.com/queue");

        // Act
        sqsAlerter.send(result);
        
        // Assert
        // Verify that send method is not called with empty ID
        verify(sqsAlerter, never()).send("http://example.com/queue", result, "", "alert");
    }
}
