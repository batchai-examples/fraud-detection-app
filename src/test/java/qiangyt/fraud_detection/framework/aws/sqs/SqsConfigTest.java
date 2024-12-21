package qiangyt.fraud_detection.framework.aws.sqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for SqsConfig.
 */
public class SqsConfigTest {

    @InjectMocks
    private SqsConfig sqsConfig;

    @Mock
    private AwsProps props;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for happy path: retrieving the AWS region.
     */
    @Test
    public void testGetRegion_HappyPath() {
        // Arrange
        String expectedRegion = "us-west-2";
        when(props.getRegion()).thenReturn(expectedRegion);

        // Act
        Region region = sqsConfig.getRegion();

        // Assert
        assertEquals(expectedRegion, region.id());
    }

    /**
     * Test case for creating SQS client with valid credentials.
     */
    @Test
    public void testCreateClient_ValidCredentials() {
        // Arrange
        String accessKeyId = "validAccessKeyId";
        String accessKeySecret = "validAccessKeySecret";
        String regionId = "us-west-2";

        when(props.getAccessKeyId()).thenReturn(accessKeyId);
        when(props.getAccessKeySecret()).thenReturn(accessKeySecret);
        when(props.getRegion()).thenReturn(regionId);

        Region region = sqsConfig.getRegion();

        // Act
        SqsClient client = sqsConfig.createClient(region);

        // Assert
        assertNotNull(client);
        assertEquals(regionId, client.serviceConfiguration().region().id());
    }

    /**
     * Test case for creating SQS client with missing access key ID.
     */
    @Test
    public void testCreateClient_MissingAccessKeyId() {
        // Arrange
        when(props.getAccessKeyId()).thenReturn(null);
        when(props.getAccessKeySecret()).thenReturn("validAccessKeySecret");

        Region region = sqsConfig.getRegion();

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            sqsConfig.createClient(region);
        });
        assertEquals("Access key ID cannot be null", exception.getMessage());
    }

    /**
     * Test case for creating SQS client with missing access key secret.
     */
    @Test
    public void testCreateClient_MissingAccessKeySecret() {
        // Arrange
        when(props.getAccessKeyId()).thenReturn("validAccessKeyId");
        when(props.getAccessKeySecret()).thenReturn(null);

        Region region = sqsConfig.getRegion();

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            sqsConfig.createClient(region);
        });
        assertEquals("Access key secret cannot be null", exception.getMessage());
    }

    /**
     * Test case for creating SQS client with invalid region.
     */
    @Test
    public void testCreateClient_InvalidRegion() {
        // Arrange
        when(props.getAccessKeyId()).thenReturn("validAccessKeyId");
        when(props.getAccessKeySecret()).thenReturn("validAccessKeySecret");
        when(props.getRegion()).thenReturn("invalid-region");

        // Act
        Region region = sqsConfig.getRegion();

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sqsConfig.createClient(region);
        });
        assertEquals("Invalid region: invalid-region", exception.getMessage());
    }
}
