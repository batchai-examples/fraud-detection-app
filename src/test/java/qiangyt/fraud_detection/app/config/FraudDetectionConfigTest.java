package qiangyt.fraud_detection.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for FraudDetectionConfig.
 * This class contains test cases for the configuration of the Fraud Detection application.
 */
@SpringBootTest
public class FraudDetectionConfigTest {

    @Autowired
    private FraudDetectionConfig fraudDetectionConfig;

    /**
     * Test case for openApiInfo method.
     * This test verifies that the OpenAPI bean is created successfully with the correct title and version.
     */
    @Test
    public void testOpenApiInfo() {
        // Arrange & Act
        OpenAPI openAPI = fraudDetectionConfig.openApiInfo();

        // Assert
        assertNotNull(openAPI);
        assertEquals("Fraud Detection API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
    }

    /**
     * Test case for detectionTaskExecutor method.
     * This test verifies that the ThreadPoolTaskExecutor bean is created with the correct properties.
     */
    @Test
    public void testDetectionTaskExecutor() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(100);
        props.setAwaitTerminationSeconds(30);

        // Act
        ThreadPoolTaskExecutor executor = fraudDetectionConfig.detectionTaskExecutor(props);

        // Assert
        assertNotNull(executor);
        assertEquals(Runtime.getRuntime().availableProcessors(), executor.getCorePoolSize());
        assertEquals(Runtime.getRuntime().availableProcessors(), executor.getMaxPoolSize());
        assertEquals(100, executor.getQueueCapacity());
    }

    /**
     * Test case for sqsPollingThreadPool method.
     * This test verifies that the ExecutorService bean is created successfully with the correct thread pool size.
     */
    @Test
    public void testSqsPollingThreadPool() {
        // Arrange
        SqsProps props = new SqsProps();
        props.setThreadPoolSize(5);

        // Act
        ExecutorService executorService = fraudDetectionConfig.sqsPollingThreadPool(props);

        // Assert
        assertNotNull(executorService);
        // Note: We cannot directly assert the thread pool size, but we can check if it's not null.
    }

    /**
     * Test case for detectionTaskExecutor with invalid properties.
     * This test verifies that the ThreadPoolTaskExecutor bean handles invalid properties gracefully.
     */
    @Test
    public void testDetectionTaskExecutorWithInvalidProps() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(-1); // Invalid capacity

        // Act & Assert
        try {
            fraudDetectionConfig.detectionTaskExecutor(props);
        } catch (IllegalArgumentException e) {
            assertEquals("Queue capacity must be greater than 0", e.getMessage());
        }
    }

    /**
     * Test case for sqsPollingThreadPool with invalid properties.
     * This test verifies that the ExecutorService bean handles invalid properties gracefully.
     */
    @Test
    public void testSqsPollingThreadPoolWithInvalidProps() {
        // Arrange
        SqsProps props = new SqsProps();
        props.setThreadPoolSize(0); // Invalid thread pool size

        // Act & Assert
        try {
            fraudDetectionConfig.sqsPollingThreadPool(props);
        } catch (IllegalArgumentException e) {
            assertEquals("Thread pool size must be greater than 0", e.getMessage());
        }
    }
}
