package qiangyt.fraud_detection.app.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for ChainedDetectionEngine.
 */
public class ChainedDetectionEngineTest {

    @InjectMocks
    private ChainedDetectionEngine detectionEngine;

    @Mock
    private BigAmountRule bigAmountRule;

    @Mock
    private SuspiciousAccountRule suspiciousAccountRule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for detecting fraud when a big amount rule triggers.
     * Expected result: FraudCategory.BIG_AMOUNT
     */
    @Test
    public void testDetect_FraudDetectedByBigAmountRule() {
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.BIG_AMOUNT);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.NONE);

        FraudCategory result = detectionEngine.detect(entity);

        // Assert that the detected fraud category is BIG_AMOUNT
        assertEquals(FraudCategory.BIG_AMOUNT, result);
    }

    /**
     * Test case for detecting fraud when a suspicious account rule triggers.
     * Expected result: FraudCategory.SUSPICIOUS_ACCOUNT
     */
    @Test
    public void testDetect_FraudDetectedBySuspiciousAccountRule() {
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.SUSPICIOUS_ACCOUNT);

        FraudCategory result = detectionEngine.detect(entity);

        // Assert that the detected fraud category is SUSPICIOUS_ACCOUNT
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }

    /**
     * Test case for detecting fraud when no rules trigger.
     * Expected result: FraudCategory.NONE
     */
    @Test
    public void testDetect_NoFraudDetected() {
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.NONE);

        FraudCategory result = detectionEngine.detect(entity);

        // Assert that the detected fraud category is NONE
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for adding a new detection rule to the chain.
     * Expected result: The rule should be added successfully.
     */
    @Test
    public void testAddRule() {
        DetectionRule newRule = new DetectionRule() {
            @Override
            public FraudCategory detect(DetectionReqEntity entity) {
                return FraudCategory.NONE;
            }
        };

        detectionEngine.addRule(newRule);

        // Assert that the new rule has been added to the rules chain
        assertEquals(1, detectionEngine.getRulesChain().size());
        assertEquals(newRule, detectionEngine.getRulesChain().get(0));
    }

    /**
     * Test case for adding multiple rules to the chain.
     * Expected result: All rules should be added successfully.
     */
    @Test
    public void testAddMultipleRules() {
        DetectionRule rule1 = new DetectionRule() {
            @Override
            public FraudCategory detect(DetectionReqEntity entity) {
                return FraudCategory.NONE;
            }
        };

        DetectionRule rule2 = new DetectionRule() {
            @Override
            public FraudCategory detect(DetectionReqEntity entity) {
                return FraudCategory.NONE;
            }
        };

        detectionEngine.addRule(rule1);
        detectionEngine.addRule(rule2);

        // Assert that both rules have been added to the rules chain
        assertEquals(2, detectionEngine.getRulesChain().size());
    }

    /**
     * Test case for detecting fraud with an empty detection request entity.
     * Expected result: FraudCategory.NONE
     */
    @Test
    public void testDetect_EmptyEntity() {
        DetectionReqEntity entity = new DetectionReqEntity(); // Empty entity
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.NONE);

        FraudCategory result = detectionEngine.detect(entity);

        // Assert that the detected fraud category is NONE
        assertEquals(FraudCategory.NONE, result);
    }
}
