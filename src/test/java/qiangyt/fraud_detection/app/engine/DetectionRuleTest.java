package qiangyt.fraud_detection.app.engine;

import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the DetectionRule functional interface.
 */
public class DetectionRuleTest {

    /**
     * Test case for detecting a fraud category when the input entity is valid.
     * This is a happy path test case.
     */
    @Test
    public void testDetectValidEntity() {
        // Arrange: Create a valid DetectionReqEntity
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setSomeField("validData"); // Set fields as required for a valid case

        // Act: Create a DetectionRule implementation and invoke detect method
        DetectionRule rule = (DetectionReqEntity req) -> FraudCategory.SUSPICIOUS; // Mock implementation
        FraudCategory result = rule.detect(entity);

        // Assert: Verify that the detected fraud category is as expected
        assertEquals(FraudCategory.SUSPICIOUS, result);
    }

    /**
     * Test case for detecting a fraud category when the input entity is null.
     * This tests the negative case where the entity is not provided.
     */
    @Test
    public void testDetectNullEntity() {
        // Arrange: Set the entity to null
        DetectionReqEntity entity = null;

        // Act: Create a DetectionRule implementation and invoke detect method
        DetectionRule rule = (DetectionReqEntity req) -> {
            if (req == null) {
                return FraudCategory.UNKNOWN; // Handle null case
            }
            return FraudCategory.NORMAL; // Default case
        };
        FraudCategory result = rule.detect(entity);

        // Assert: Verify that the detected fraud category is UNKNOWN
        assertEquals(FraudCategory.UNKNOWN, result);
    }

    /**
     * Test case for detecting a fraud category when the input entity has invalid data.
     * This tests a corner case where the entity data is not valid.
     */
    @Test
    public void testDetectInvalidData() {
        // Arrange: Create a DetectionReqEntity with invalid data
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setSomeField("invalidData"); // Set fields to simulate invalid data

        // Act: Create a DetectionRule implementation and invoke detect method
        DetectionRule rule = (DetectionReqEntity req) -> FraudCategory.FRAUDULENT; // Mock implementation
        FraudCategory result = rule.detect(entity);

        // Assert: Verify that the detected fraud category is FRAUDULENT
        assertEquals(FraudCategory.FRAUDULENT, result);
    }

    /**
     * Test case for detecting a fraud category when the input entity is empty.
     * This tests a corner case where the entity is present but has no data.
     */
    @Test
    public void testDetectEmptyEntity() {
        // Arrange: Create an empty DetectionReqEntity
        DetectionReqEntity entity = new DetectionReqEntity();
        // Assume all fields are set to default values (empty)

        // Act: Create a DetectionRule implementation and invoke detect method
        DetectionRule rule = (DetectionReqEntity req) -> FraudCategory.NORMAL; // Mock implementation
        FraudCategory result = rule.detect(entity);

        // Assert: Verify that the detected fraud category is NORMAL
        assertEquals(FraudCategory.NORMAL, result);
    }

    /**
     * Test case for detecting a fraud category when the input entity has a specific known value.
     * This tests a positive case with a specific input.
     */
    @Test
    public void testDetectKnownValue() {
        // Arrange: Create a DetectionReqEntity with a known value
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setSomeField("knownValue"); // Set fields to simulate a known case

        // Act: Create a DetectionRule implementation and invoke detect method
        DetectionRule rule = (DetectionReqEntity req) -> FraudCategory.SUSPICIOUS; // Mock implementation
        FraudCategory result = rule.detect(entity);

        // Assert: Verify that the detected fraud category is SUSPICIOUS
        assertEquals(FraudCategory.SUSPICIOUS, result);
    }
}
