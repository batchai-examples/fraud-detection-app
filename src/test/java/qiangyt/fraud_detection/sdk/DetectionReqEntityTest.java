package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

/**
 * Unit tests for the DetectionReqEntity class.
 * This class tests various scenarios including happy path, positive cases, negative cases, and corner cases.
 */
public class DetectionReqEntityTest {

    /**
     * Test case for creating a DetectionReqEntity with valid parameters.
     * This represents the happy path scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_HappyPath() {
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("12345")
                .amount(100)
                .memo("Test transaction")
                .id("req-001")
                .receivedAt(new Date())
                .build();

        // Verify that the entity is created with the correct values
        assertEquals("12345", entity.getAccountId());
        assertEquals(100, entity.getAmount());
        assertEquals("Test transaction", entity.getMemo());
        assertEquals("req-001", entity.getId());
        assertNotNull(entity.getReceivedAt());
    }

    /**
     * Test case for creating a DetectionReqEntity with a negative amount.
     * This represents a negative case scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_NegativeAmount() {
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("12345")
                .amount(-50)
                .memo("Negative amount transaction")
                .id("req-002")
                .receivedAt(new Date())
                .build();

        // Verify that the entity is created with the negative amount
        assertEquals(-50, entity.getAmount());
    }

    /**
     * Test case for creating a DetectionReqEntity with an empty account ID.
     * This represents a corner case scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_EmptyAccountId() {
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("")
                .amount(100)
                .memo("Empty account ID transaction")
                .id("req-003")
                .receivedAt(new Date())
                .build();

        // Verify that the account ID is empty
        assertEquals("", entity.getAccountId());
    }

    /**
     * Test case for creating a DetectionReqEntity with a null memo.
     * This represents a positive case scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_NullMemo() {
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("12345")
                .amount(100)
                .memo(null)
                .id("req-004")
                .receivedAt(new Date())
                .build();

        // Verify that the memo is null
        assertNull(entity.getMemo());
    }

    /**
     * Test case for creating a DetectionReqEntity with a very large amount.
     * This represents a corner case scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_LargeAmount() {
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("12345")
                .amount(Integer.MAX_VALUE)
                .memo("Large amount transaction")
                .id("req-005")
                .receivedAt(new Date())
                .build();

        // Verify that the entity is created with the large amount
        assertEquals(Integer.MAX_VALUE, entity.getAmount());
    }

    /**
     * Test case for creating a DetectionReqEntity with a future date.
     * This represents a corner case scenario.
     */
    @Test
    public void testCreateDetectionReqEntity_FutureDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 1000000000); // 1000 seconds in the future
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("12345")
                .amount(100)
                .memo("Future date transaction")
                .id("req-006")
                .receivedAt(futureDate)
                .build();

        // Verify that the receivedAt date is in the future
        assertTrue(entity.getReceivedAt().after(new Date()));
    }
}
