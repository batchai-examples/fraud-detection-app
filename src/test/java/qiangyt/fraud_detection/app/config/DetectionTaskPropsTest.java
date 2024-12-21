package qiangyt.fraud_detection.app.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DetectionTaskProps class.
 * This class tests the configuration properties for the detection task.
 */
public class DetectionTaskPropsTest {

    /**
     * Test the default values of DetectionTaskProps.
     * This test checks if the default values are set correctly.
     */
    @Test
    public void testDefaultValues() {
        DetectionTaskProps props = new DetectionTaskProps();
        
        // Assert that the default queue capacity is 500
        assertEquals(500, props.getQueueCapacity(), "Default queue capacity should be 500");
        
        // Assert that the default await termination seconds is 60
        assertEquals(60, props.getAwaitTerminationSeconds(), "Default await termination seconds should be 60");
    }

    /**
     * Test setting custom values for queue capacity.
     * This test checks if the queue capacity can be set to a custom value.
     */
    @Test
    public void testSetCustomQueueCapacity() {
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(1000);
        
        // Assert that the queue capacity is set to 1000
        assertEquals(1000, props.getQueueCapacity(), "Queue capacity should be set to 1000");
    }

    /**
     * Test setting custom values for await termination seconds.
     * This test checks if the await termination seconds can be set to a custom value.
     */
    @Test
    public void testSetCustomAwaitTerminationSeconds() {
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds(120);
        
        // Assert that the await termination seconds is set to 120
        assertEquals(120, props.getAwaitTerminationSeconds(), "Await termination seconds should be set to 120");
    }

    /**
     * Test setting negative value for queue capacity.
     * This test checks if setting a negative queue capacity throws an exception.
     */
    @Test
    public void testSetNegativeQueueCapacity() {
        DetectionTaskProps props = new DetectionTaskProps();
        
        // Expect an IllegalArgumentException when setting a negative queue capacity
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            props.setQueueCapacity(-1);
        });
        
        // Assert that the exception message is as expected
        assertEquals("Queue capacity must be non-negative", exception.getMessage());
    }

    /**
     * Test setting negative value for await termination seconds.
     * This test checks if setting a negative await termination seconds throws an exception.
     */
    @Test
    public void testSetNegativeAwaitTerminationSeconds() {
        DetectionTaskProps props = new DetectionTaskProps();
        
        // Expect an IllegalArgumentException when setting a negative await termination seconds
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            props.setAwaitTerminationSeconds(-1);
        });
        
        // Assert that the exception message is as expected
        assertEquals("Await termination seconds must be non-negative", exception.getMessage());
    }

    /**
     * Test setting zero value for queue capacity.
     * This test checks if setting a zero queue capacity is allowed.
     */
    @Test
    public void testSetZeroQueueCapacity() {
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(0);
        
        // Assert that the queue capacity is set to 0
        assertEquals(0, props.getQueueCapacity(), "Queue capacity should be set to 0");
    }
}
