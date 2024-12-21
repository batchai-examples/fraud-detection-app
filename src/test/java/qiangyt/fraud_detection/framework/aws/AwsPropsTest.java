package qiangyt.fraud_detection.framework.aws;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AwsProps.
 */
public class AwsPropsTest {

    /**
     * Test the default values of AwsProps.
     * This test checks if the default values are set correctly.
     */
    @Test
    public void testDefaultValues() {
        AwsProps awsProps = new AwsProps();
        
        // Check if the default region is null (not set)
        assertNull(awsProps.getRegion(), "Default region should be null");
        
        // Check if the default access key ID is null (not set)
        assertNull(awsProps.getAccessKeyId(), "Default access key ID should be null");
        
        // Check if the default access key secret is null (not set)
        assertNull(awsProps.getAccessKeySecret(), "Default access key secret should be null");
    }

    /**
     * Test setting and getting AWS region.
     * This test checks if the region can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetRegion() {
        AwsProps awsProps = new AwsProps();
        String expectedRegion = "us-west-2";
        
        // Set the region
        awsProps.setRegion(expectedRegion);
        
        // Verify the region is set correctly
        assertEquals(expectedRegion, awsProps.getRegion(), "Region should match the expected value");
    }

    /**
     * Test setting and getting AWS access key ID.
     * This test checks if the access key ID can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetAccessKeyId() {
        AwsProps awsProps = new AwsProps();
        String expectedAccessKeyId = "AKIA123456789";
        
        // Set the access key ID
        awsProps.setAccessKeyId(expectedAccessKeyId);
        
        // Verify the access key ID is set correctly
        assertEquals(expectedAccessKeyId, awsProps.getAccessKeyId(), "Access key ID should match the expected value");
    }

    /**
     * Test setting and getting AWS access key secret.
     * This test checks if the access key secret can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetAccessKeySecret() {
        AwsProps awsProps = new AwsProps();
        String expectedAccessKeySecret = "secret123456789";
        
        // Set the access key secret
        awsProps.setAccessKeySecret(expectedAccessKeySecret);
        
        // Verify the access key secret is set correctly
        assertEquals(expectedAccessKeySecret, awsProps.getAccessKeySecret(), "Access key secret should match the expected value");
    }

    /**
     * Test setting all properties at once.
     * This test checks if all properties can be set and retrieved correctly.
     */
    @Test
    public void testSetAllProperties() {
        AwsProps awsProps = new AwsProps();
        String expectedRegion = "us-east-1";
        String expectedAccessKeyId = "AKIA987654321";
        String expectedAccessKeySecret = "secret987654321";
        
        // Set all properties
        awsProps.setRegion(expectedRegion);
        awsProps.setAccessKeyId(expectedAccessKeyId);
        awsProps.setAccessKeySecret(expectedAccessKeySecret);
        
        // Verify all properties are set correctly
        assertEquals(expectedRegion, awsProps.getRegion(), "Region should match the expected value");
        assertEquals(expectedAccessKeyId, awsProps.getAccessKeyId(), "Access key ID should match the expected value");
        assertEquals(expectedAccessKeySecret, awsProps.getAccessKeySecret(), "Access key secret should match the expected value");
    }
}
