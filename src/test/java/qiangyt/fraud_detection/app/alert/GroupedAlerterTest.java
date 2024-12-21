package qiangyt.fraud_detection.app.alert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import qiangyt.fraud_detection.sdk.DetectionResult;

import static org.mockito.Mockito.*;

/**
 * GroupedAlerterTest contains test cases for the GroupedAlerter class.
 * It tests the registration of alerters and the sending of alerts.
 */
public class GroupedAlerterTest {

    private GroupedAlerter groupedAlerter;
    private Alerter mockAlerter1;
    private Alerter mockAlerter2;
    private DetectionResult mockDetectionResult;

    @BeforeEach
    public void setUp() {
        groupedAlerter = new GroupedAlerter();
        mockAlerter1 = Mockito.mock(Alerter.class);
        mockAlerter2 = Mockito.mock(Alerter.class);
        mockDetectionResult = Mockito.mock(DetectionResult.class);
    }

    /**
     * Test case for successfully registering an alerter.
     * It verifies that the alerter is added to the list of alerters.
     */
    @Test
    public void testRegisterAlerter() {
        // Register the first alerter
        groupedAlerter.registerAlerter(mockAlerter1);
        // Verify that the alerter is registered
        assert(groupedAlerter.getAlerters().contains(mockAlerter1));
    }

    /**
     * Test case for sending an alert when there are registered alerters.
     * It verifies that the send method is called on each registered alerter.
     */
    @Test
    public void testSendAlertToRegisteredAlerters() {
        // Register two alerters
        groupedAlerter.registerAlerter(mockAlerter1);
        groupedAlerter.registerAlerter(mockAlerter2);
        
        // Send the alert
        groupedAlerter.send(mockDetectionResult);
        
        // Verify that send was called on both alerters
        verify(mockAlerter1, times(1)).send(mockDetectionResult);
        verify(mockAlerter2, times(1)).send(mockDetectionResult);
    }

    /**
     * Test case for handling an exception thrown by an alerter during alert sending.
     * It verifies that the exception is caught and logged, allowing the process to continue.
     */
    @Test
    public void testSendAlertWithAlerterException() {
        // Register an alerter that throws an exception
        doThrow(new RuntimeException("Alerter failed")).when(mockAlerter1).send(mockDetectionResult);
        groupedAlerter.registerAlerter(mockAlerter1);
        groupedAlerter.registerAlerter(mockAlerter2);
        
        // Send the alert
        groupedAlerter.send(mockDetectionResult);
        
        // Verify that send was called on the second alerter despite the first one failing
        verify(mockAlerter2, times(1)).send(mockDetectionResult);
    }

    /**
     * Test case for sending an alert when no alerters are registered.
     * It verifies that no exceptions are thrown and the method completes successfully.
     */
    @Test
    public void testSendAlertWithNoRegisteredAlerters() {
        // Send the alert with no registered alerters
        groupedAlerter.send(mockDetectionResult);
        // No exceptions should be thrown and the method should complete
    }

    /**
     * Test case for registering multiple alerters and ensuring they are all registered.
     * It verifies that all registered alerters are present in the list.
     */
    @Test
    public void testRegisterMultipleAlerters() {
        // Register multiple alerters
        groupedAlerter.registerAlerter(mockAlerter1);
        groupedAlerter.registerAlerter(mockAlerter2);
        
        // Verify that both alerters are registered
        assert(groupedAlerter.getAlerters().contains(mockAlerter1));
        assert(groupedAlerter.getAlerters().contains(mockAlerter2));
    }
}
