package qiangyt.fraud_detection.app.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

/**
 * Unit tests for RuleProps class.
 *
 * <p>This class contains tests for the RuleProps configuration properties,
 * ensuring that the default values are set correctly and that the properties
 * can be modified as expected.
 */
public class RulePropsTest {

    /**
     * Test to verify the default maximum transaction amount.
     * 
     * <p>This test checks that the default value of maxTransactionAmount is 100000.
     */
    @Test
    public void testDefaultMaxTransactionAmount() {
        RuleProps ruleProps = new RuleProps();
        // Assert that the default maxTransactionAmount is 100000
        assertEquals(100000, ruleProps.getMaxTransactionAmount());
    }

    /**
     * Test to verify the default suspicious accounts.
     * 
     * <p>This test checks that the default suspicious accounts contain "cgrant" and "fbiden".
     */
    @Test
    public void testDefaultSuspiciousAccounts() {
        RuleProps ruleProps = new RuleProps();
        // Assert that the default suspicious accounts are as expected
        assertEquals(Set.of("cgrant", "fbiden"), ruleProps.getSuspiciousAccounts());
    }

    /**
     * Test to set and get a custom maximum transaction amount.
     * 
     * <p>This test checks that we can set a custom value for maxTransactionAmount
     * and retrieve it correctly.
     */
    @Test
    public void testSetMaxTransactionAmount() {
        RuleProps ruleProps = new RuleProps();
        ruleProps.setMaxTransactionAmount(50000);
        // Assert that the maxTransactionAmount is updated correctly
        assertEquals(50000, ruleProps.getMaxTransactionAmount());
    }

    /**
     * Test to set and get custom suspicious accounts.
     * 
     * <p>This test checks that we can set a custom set of suspicious accounts
     * and retrieve it correctly.
     */
    @Test
    public void testSetSuspiciousAccounts() {
        RuleProps ruleProps = new RuleProps();
        ruleProps.setSuspiciousAccounts(Set.of("newUser", "anotherUser"));
        // Assert that the suspicious accounts are updated correctly
        assertEquals(Set.of("newUser", "anotherUser"), ruleProps.getSuspiciousAccounts());
    }

    /**
     * Test to verify behavior with empty suspicious accounts.
     * 
     * <p>This test checks that setting suspicious accounts to an empty set
     * works as expected.
     */
    @Test
    public void testSetEmptySuspiciousAccounts() {
        RuleProps ruleProps = new RuleProps();
        ruleProps.setSuspiciousAccounts(Set.of());
        // Assert that the suspicious accounts are now empty
        assertTrue(ruleProps.getSuspiciousAccounts().isEmpty());
    }

    /**
     * Test to verify behavior with negative transaction amount.
     * 
     * <p>This test checks that setting a negative transaction amount
     * is handled correctly (in this case, we expect it to be set).
     */
    @Test
    public void testSetNegativeTransactionAmount() {
        RuleProps ruleProps = new RuleProps();
        ruleProps.setMaxTransactionAmount(-1000);
        // Assert that the maxTransactionAmount is set to -1000
        assertEquals(-1000, ruleProps.getMaxTransactionAmount());
    }
}
