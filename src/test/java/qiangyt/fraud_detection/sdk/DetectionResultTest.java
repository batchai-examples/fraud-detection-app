package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

/**
 * Test class for DetectionResult.
 */
public class DetectionResultTest {

    /**
     * Test case for creating a DetectionResult with a valid entity and category.
     * This is a happy path scenario.
     */
    @Test
    public void testFrom_ValidEntityAndCategory() {
        // Given a valid DetectionReqEntity and FraudCategory
        DetectionReqEntity entity = new DetectionReqEntity(); // Assuming a default constructor exists
        FraudCategory category = new FraudCategory("Fraud", true, "Fraud detected");

        // When creating a DetectionResult
        DetectionResult result = DetectionResult.from(entity, category);

        // Then the result should not be null and should have the expected properties
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertTrue(result.isFraudulent());
        assertEquals(category, result.getCategory());
        assertEquals(category.getMessage(), result.getMessage());
        assertNotNull(result.getDetectedAt());
    }

    /**
     * Test case for creating a DetectionResult with a null entity.
     * This tests the negative case where the entity is null.
     */
    @Test
    public void testFrom_NullEntity() {
        // Given a null DetectionReqEntity
        DetectionReqEntity entity = null;
        FraudCategory category = new FraudCategory("Fraud", true, "Fraud detected");

        // When creating a DetectionResult
        DetectionResult result = DetectionResult.from(entity, category);

        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for creating a DetectionResult with a valid entity but null category.
     * This tests the corner case where the category is null.
     */
    @Test
    public void testFrom_ValidEntityNullCategory() {
        // Given a valid DetectionReqEntity and a null FraudCategory
        DetectionReqEntity entity = new DetectionReqEntity(); // Assuming a default constructor exists
        FraudCategory category = null;

        // When creating a DetectionResult
        DetectionResult result = DetectionResult.from(entity, category);

        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for creating a DetectionResult with an empty category.
     * This tests the corner case where the category is empty.
     */
    @Test
    public void testFrom_ValidEntityEmptyCategory() {
        // Given a valid DetectionReqEntity and an empty FraudCategory
        DetectionReqEntity entity = new DetectionReqEntity(); // Assuming a default constructor exists
        FraudCategory category = new FraudCategory("", false, "");

        // When creating a DetectionResult
        DetectionResult result = DetectionResult.from(entity, category);

        // Then the result should not be null but should have the expected properties
        assertNotNull(result);
        assertEquals("", result.getCategory().getName());
        assertFalse(result.isFraudulent());
        assertEquals("", result.getMessage());
    }

    /**
     * Test case for creating a DetectionResult with a valid entity and category.
     * This tests a positive case where the category indicates fraud.
     */
    @Test
    public void testFrom_ValidEntityFraudCategory() {
        // Given a valid DetectionReqEntity and a FraudCategory indicating fraud
        DetectionReqEntity entity = new DetectionReqEntity(); // Assuming a default constructor exists
        FraudCategory category = new FraudCategory("Fraud", true, "Fraud detected");

        // When creating a DetectionResult
        DetectionResult result = DetectionResult.from(entity, category);

        // Then the result should indicate that fraud was detected
        assertTrue(result.isFraudulent());
        assertEquals("Fraud detected", result.getMessage());
    }
}
