package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UuidHelper.
 */
public class UuidHelperTest {

    /**
     * Test the shortUuid method to ensure it generates a valid compressed UUID.
     * This is a happy path test case.
     */
    @Test
    public void testShortUuid() {
        String shortUuid = UuidHelper.shortUuid();
        // Check that the generated UUID is not null and has the correct length
        assertNotNull(shortUuid);
        assertEquals(22, shortUuid.length());
    }

    /**
     * Test the compress method with a valid UUID.
     * This is a happy path test case.
     */
    @Test
    public void testCompress() {
        UUID uuid = UUID.randomUUID();
        String compressedUuid = UuidHelper.compress(uuid);
        // Check that the compressed UUID is not null and has the correct length
        assertNotNull(compressedUuid);
        assertEquals(22, compressedUuid.length());
    }

    /**
     * Test the uncompress method with a valid compressed UUID.
     * This is a happy path test case.
     */
    @Test
    public void testUncompress() {
        UUID originalUuid = UUID.randomUUID();
        String compressedUuid = UuidHelper.compress(originalUuid);
        UUID decompressedUuid = UuidHelper.uncompress(compressedUuid);
        // Check that the decompressed UUID matches the original UUID
        assertEquals(originalUuid, decompressedUuid);
    }

    /**
     * Test the uncompress method with an invalid compressed UUID length.
     * This is a negative test case.
     */
    @Test
    public void testUncompressInvalidLength() {
        String invalidCompressedUuid = "shortuuidstring"; // Invalid length
        // Expect IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            UuidHelper.uncompress(invalidCompressedUuid);
        });
    }

    /**
     * Test the uncompress method with an empty string.
     * This is a corner case test case.
     */
    @Test
    public void testUncompressEmptyString() {
        String emptyCompressedUuid = ""; // Empty string
        // Expect IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            UuidHelper.uncompress(emptyCompressedUuid);
        });
    }

    /**
     * Test the uncompress method with a null input.
     * This is a corner case test case.
     */
    @Test
    public void testUncompressNull() {
        // Expect IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            UuidHelper.uncompress(null);
        });
    }
}
