package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FraudCategory enum.
 */
public class FraudCategoryTest {

    /**
     * Test to verify that NONE category does not represent fraud.
     * This is a happy path test case.
     */
    @Test
    public void testNoneCategory() {
        // Verify that NONE category does not represent fraud
        assertFalse(FraudCategory.NONE.yes);
        // Verify the message for NONE category
        assertEquals("none", FraudCategory.NONE.message);
    }

    /**
     * Test to verify that BIG_AMOUNT category represents fraud.
     * This is a happy path test case.
     */
    @Test
    public void testBigAmountCategory() {
        // Verify that BIG_AMOUNT category represents fraud
        assertTrue(FraudCategory.BIG_AMOUNT.yes);
        // Verify the message for BIG_AMOUNT category
        assertEquals("the transaction amount exceeds a threshold", FraudCategory.BIG_AMOUNT.message);
    }

    /**
     * Test to verify that SUSPICIOUS_ACCOUNT category represents fraud.
     * This is a happy path test case.
     */
    @Test
    public void testSuspiciousAccountCategory() {
        // Verify that SUSPICIOUS_ACCOUNT category represents fraud
        assertTrue(FraudCategory.SUSPICIOUS_ACCOUNT.yes);
        // Verify the message for SUSPICIOUS_ACCOUNT category
        assertEquals("the transaction originates from a suspicious account", FraudCategory.SUSPICIOUS_ACCOUNT.message);
    }

    /**
     * Test to verify that the FraudCategory enum has the correct number of constants.
     * This is a corner case test case.
     */
    @Test
    public void testFraudCategoryCount() {
        // Verify that there are exactly 3 categories in FraudCategory enum
        assertEquals(3, FraudCategory.values().length);
    }

    /**
     * Test to ensure that the messages are not null for any fraud category.
     * This is a positive case test case.
     */
    @Test
    public void testFraudCategoryMessagesNotNull() {
        // Verify that messages for all categories are not null
        for (FraudCategory category : FraudCategory.values()) {
            assertNotNull(category.message);
        }
    }

    /**
     * Test to ensure that the yes field is correctly set for each category.
     * This is a negative case test case.
     */
    @Test
    public void testFraudCategoryYesField() {
        // Verify that the yes field is correctly set for each category
        assertFalse(FraudCategory.NONE.yes);
        assertTrue(FraudCategory.BIG_AMOUNT.yes);
        assertTrue(FraudCategory.SUSPICIOUS_ACCOUNT.yes);
    }
}
