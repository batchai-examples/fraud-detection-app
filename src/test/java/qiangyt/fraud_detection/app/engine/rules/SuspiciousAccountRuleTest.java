package qiangyt.fraud_detection.app.engine.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.app.engine.ChainedDetectionEngine;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for SuspiciousAccountRule.
 * This class contains test cases to verify the behavior of the SuspiciousAccountRule class.
 */
public class SuspiciousAccountRuleTest {

    @InjectMocks
    private SuspiciousAccountRule suspiciousAccountRule;

    @Mock
    private RuleProps props;

    @Mock
    private ChainedDetectionEngine chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        suspiciousAccountRule.init(); // Initialize the rule and add it to the chain
    }

    /**
     * Test case for detecting a suspicious account.
     * This test verifies that the detect method returns SUSPICIOUS_ACCOUNT
     * when the account ID is in the list of suspicious accounts.
     */
    @Test
    void testDetectSuspiciousAccount() {
        // Arrange
        String suspiciousAccountId = "12345";
        when(props.getSuspiciousAccounts()).thenReturn(Collections.singletonList(suspiciousAccountId));
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(suspiciousAccountId);

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }

    /**
     * Test case for detecting a non-suspicious account.
     * This test verifies that the detect method returns NONE
     * when the account ID is not in the list of suspicious accounts.
     */
    @Test
    void testDetectNonSuspiciousAccount() {
        // Arrange
        String nonSuspiciousAccountId = "67890";
        when(props.getSuspiciousAccounts()).thenReturn(Collections.singletonList("12345"));
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(nonSuspiciousAccountId);

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for detecting a suspicious account with multiple accounts.
     * This test verifies that the detect method correctly identifies a suspicious account
     * when multiple accounts are present in the list.
     */
    @Test
    void testDetectSuspiciousAccountMultiple() {
        // Arrange
        String suspiciousAccountId = "12345";
        String anotherSuspiciousAccountId = "54321";
        when(props.getSuspiciousAccounts()).thenReturn(Arrays.asList(suspiciousAccountId, anotherSuspiciousAccountId));
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(suspiciousAccountId);

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }

    /**
     * Test case for an empty list of suspicious accounts.
     * This test verifies that the detect method returns NONE
     * when there are no suspicious accounts defined.
     */
    @Test
    void testDetectWithEmptySuspiciousAccounts() {
        // Arrange
        when(props.getSuspiciousAccounts()).thenReturn(Collections.emptyList());
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId("anyAccountId");

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for detecting a suspicious account with null account ID.
     * This test verifies that the detect method handles null account IDs gracefully
     * and returns NONE.
     */
    @Test
    void testDetectWithNullAccountId() {
        // Arrange
        when(props.getSuspiciousAccounts()).thenReturn(Collections.singletonList("12345"));
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(null);

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for detecting a suspicious account with an empty account ID.
     * This test verifies that the detect method returns NONE
     * when the account ID is an empty string.
     */
    @Test
    void testDetectWithEmptyAccountId() {
        // Arrange
        when(props.getSuspiciousAccounts()).thenReturn(Collections.singletonList("12345"));
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId("");

        // Act
        FraudCategory result = suspiciousAccountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }
}
