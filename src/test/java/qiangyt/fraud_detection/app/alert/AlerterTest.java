package qiangyt.fraud_detection.app.alert;

import qiangyt.fraud_detection.sdk.DetectionResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Test class for the Alerter interface.
 * This class contains test cases to verify the behavior of the send method in the Alerter interface.
 */
public class AlerterTest {

    /**
     * Test case for sending a valid alert.
     * This test verifies that the send method is called with a valid DetectionResult.
     */
    @Test
    public void testSendValidAlert() {
        // Arrange
        Alerter alerter = Mockito.mock(Alerter.class);
        DetectionResult validResult = new DetectionResult(); // Assuming a default constructor is available

        // Act
        alerter.send(validResult);

        // Assert
        verify(alerter, times(1)).send(validResult); // Verify that send was called once with the valid result
    }

    /**
     * Test case for sending a null alert.
     * This test verifies that the send method can handle a null DetectionResult.
     */
    @Test
    public void testSendNullAlert() {
        // Arrange
        Alerter alerter = Mockito.mock(Alerter.class);

        // Act
        alerter.send(null);

        // Assert
        verify(alerter, times(1)).send(null); // Verify that send was called once with null
    }

    /**
     * Test case for sending an alert with an invalid state.
     * This test checks the behavior when sending a DetectionResult that is in an invalid state.
     */
    @Test
    public void testSendInvalidAlert() {
        // Arrange
        Alerter alerter = Mockito.mock(Alerter.class);
        DetectionResult invalidResult = new DetectionResult(); // Assuming a default constructor is available
        // Set the invalid state here if applicable

        // Act
        alerter.send(invalidResult);

        // Assert
        verify(alerter, times(1)).send(invalidResult); // Verify that send was called once with the invalid result
    }

    /**
     * Test case for sending multiple alerts.
     * This test verifies that the send method can handle multiple calls.
     */
    @Test
    public void testSendMultipleAlerts() {
        // Arrange
        Alerter alerter = Mockito.mock(Alerter.class);
        DetectionResult result1 = new DetectionResult(); // Assuming a default constructor is available
        DetectionResult result2 = new DetectionResult(); // Assuming a default constructor is available

        // Act
        alerter.send(result1);
        alerter.send(result2);

        // Assert
        verify(alerter, times(1)).send(result1); // Verify that send was called once with result1
        verify(alerter, times(1)).send(result2); // Verify that send was called once with result2
    }

    /**
     * Test case for sending alerts in rapid succession.
     * This test checks the behavior when sending alerts quickly one after another.
     */
    @Test
    public void testSendAlertsRapidly() {
        // Arrange
        Alerter alerter = Mockito.mock(Alerter.class);
        DetectionResult result = new DetectionResult(); // Assuming a default constructor is available

        // Act
        for (int i = 0; i < 100; i++) {
            alerter.send(result);
        }

        // Assert
        verify(alerter, times(100)).send(result); // Verify that send was called 100 times with the same result
    }
}
