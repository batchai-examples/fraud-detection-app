package qiangyt.fraud_detection.app.engine.rules;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.app.engine.ChainedDetectionEngine;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for BigAmountRule.
 * This class contains test cases to verify the functionality of the BigAmountRule class.
 */
public class BigAmountRuleTest {

    @InjectMocks
    private BigAmountRule bigAmountRule;

    @Mock
    private RuleProps props;

    @Mock
    private ChainedDetectionEngine chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bigAmountRule.init(); // Initialize the rule and add it to the chain
    }

    /**
     * Test case for a transaction amount that is below the maximum allowed amount.
     * Expected result: FraudCategory.NONE
     */
    @Test
    void testDetect_AmountBelowMax_ReturnsNone() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(50.0); // Set amount below max
        when(props.getMaxTransactionAmount()).thenReturn(100.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result); // Verify that the result is NONE
    }

    /**
     * Test case for a transaction amount that is equal to the maximum allowed amount.
     * Expected result: FraudCategory.BIG_AMOUNT
     */
    @Test
    void testDetect_AmountEqualToMax_ReturnsBigAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(100.0); // Set amount equal to max
        when(props.getMaxTransactionAmount()).thenReturn(100.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result); // Verify that the result is BIG_AMOUNT
    }

    /**
     * Test case for a transaction amount that exceeds the maximum allowed amount.
     * Expected result: FraudCategory.BIG_AMOUNT
     */
    @Test
    void testDetect_AmountAboveMax_ReturnsBigAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(150.0); // Set amount above max
        when(props.getMaxTransactionAmount()).thenReturn(100.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result); // Verify that the result is BIG_AMOUNT
    }

    /**
     * Test case for a transaction amount that is negative.
     * Expected result: FraudCategory.NONE
     */
    @Test
    void testDetect_NegativeAmount_ReturnsNone() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(-50.0); // Set negative amount
        when(props.getMaxTransactionAmount()).thenReturn(100.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result); // Verify that the result is NONE
    }

    /**
     * Test case for a transaction amount of zero.
     * Expected result: FraudCategory.NONE
     */
    @Test
    void testDetect_ZeroAmount_ReturnsNone() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(0.0); // Set amount to zero
        when(props.getMaxTransactionAmount()).thenReturn(100.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result); // Verify that the result is NONE
    }

    /**
     * Test case for a very large transaction amount.
     * Expected result: FraudCategory.BIG_AMOUNT
     */
    @Test
    void testDetect_VeryLargeAmount_ReturnsBigAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1_000_000.0); // Set a very large amount
        when(props.getMaxTransactionAmount()).thenReturn(100_000.0); // Mock max amount

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result); // Verify that the result is BIG_AMOUNT
    }
}
