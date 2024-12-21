package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Unit tests for the DetectionReq class.
 */
public class DetectionReqTest {

    private final Validator validator;

    public DetectionReqTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test case for a valid DetectionReq object.
     * This is a happy path test where all required fields are valid.
     */
    @Test
    public void testValidDetectionReq() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId("validAccountId");
        detectionReq.setAmount(100);
        detectionReq.setMemo("Test memo");

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertTrue(violations.isEmpty(), "Expected no validation violations for a valid DetectionReq");
    }

    /**
     * Test case for missing accountId.
     * This is a negative case where accountId is blank.
     */
    @Test
    public void testMissingAccountId() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId(""); // Invalid accountId
        detectionReq.setAmount(100);

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertFalse(violations.isEmpty(), "Expected validation violations for missing accountId");
    }

    /**
     * Test case for missing amount.
     * This is a negative case where amount is not set.
     */
    @Test
    public void testMissingAmount() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId("validAccountId");
        detectionReq.setAmount(0); // Invalid amount

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertFalse(violations.isEmpty(), "Expected validation violations for missing amount");
    }

    /**
     * Test case for valid DetectionReq with no memo.
     * This is a positive case where memo is optional.
     */
    @Test
    public void testValidDetectionReqNoMemo() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId("validAccountId");
        detectionReq.setAmount(100);
        detectionReq.setMemo(null); // Memo is optional

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertTrue(violations.isEmpty(), "Expected no validation violations for valid DetectionReq with no memo");
    }

    /**
     * Test case for negative amount.
     * This is a corner case where amount is negative.
     */
    @Test
    public void testNegativeAmount() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId("validAccountId");
        detectionReq.setAmount(-50); // Invalid amount

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertFalse(violations.isEmpty(), "Expected validation violations for negative amount");
    }

    /**
     * Test case for valid DetectionReq with large amount.
     * This is a positive case where amount is a large positive integer.
     */
    @Test
    public void testLargeAmount() {
        DetectionReq detectionReq = new DetectionReq();
        detectionReq.setAccountId("validAccountId");
        detectionReq.setAmount(Integer.MAX_VALUE); // Valid large amount

        // Validate the detectionReq object
        var violations = validator.validate(detectionReq);
        assertTrue(violations.isEmpty(), "Expected no validation violations for valid DetectionReq with large amount");
    }
}
