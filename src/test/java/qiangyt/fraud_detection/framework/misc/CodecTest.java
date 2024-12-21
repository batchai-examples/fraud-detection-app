!!!!test_begin!!!!
package qiangyt.fraud_detection.framework.misc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CodecTest {

    /**
     * Test for isBase64Encoded method with valid Base64 input.
     * This test checks if the method correctly identifies a Base64 encoded file.
     */
    @Test
    public void testIsBase64Encoded_ValidBase64() throws Exception {
        // Create a mock MultipartFile with Base64 content
        String base64Content = "U29tZSB2YWxpZCBjb250ZW50"; // "Some valid content" in Base64
        MultipartFile file = createMockMultipartFile(base64Content);

        // Call the method and assert the result
        assertTrue(Codec.isBase64Encoded(file));
    }

    /**
     * Test for isBase64Encoded method with invalid Base64 input.
     * This test checks if the method correctly identifies a non-Base64 encoded file.
     */
    @Test
    public void testIsBase64Encoded_InvalidBase64() throws Exception {
        // Create a mock MultipartFile with invalid content
        String invalidContent = "Some invalid content";
        MultipartFile file = createMockMultipartFile(invalidContent);

        // Call the method and assert the result
        assertFalse(Codec.isBase64Encoded(file));
    }

    /**
     * Test for isBase64Encoded method with empty file.
     * This test checks if the method returns false for an empty file.
     */
    @Test
    public void testIsBase64Encoded_EmptyFile() throws Exception {
        // Create a mock MultipartFile with empty content
        MultipartFile file = createMockMultipartFile("");

        // Call the method and assert the result
        assertFalse(Codec.isBase64Encoded(file));
    }

    /**
     * Test for bytesToBase64 method with valid byte array.
     * This test checks if the method correctly converts a byte array to Base64.
     */
    @Test
    public void testBytesToBase64_ValidInput() {
        byte[] input = "Hello".getBytes();
        String expected = "SGVsbG8"; // Base64 encoded "Hello"
        assertEquals(expected, Codec.bytesToBase64(input));
    }

    /**
     * Test for bytesToBase64 method with null input.
     * This test checks if the method returns null when input is null.
     */
    @Test
    public void testBytesToBase64_NullInput() {
        assertNull(Codec.bytesToBase64(null));
    }

    /**
     * Test for base64ToStr method with valid Base64 input.
     * This test checks if the method correctly decodes a Base64 string to a regular string.
     */
    @Test
    public void testBase64ToStr_ValidInput() {
        String base64Input = "SGVsbG8="; // Base64 for "Hello"
        assertEquals("Hello", Codec.base64ToStr(base64Input));
    }

    /**
     * Test for base64ToStr method with invalid Base64 input.
     * This test checks if the method returns null for invalid Base64 input.
     */
    @Test
    public void testBase64ToStr_InvalidInput() {
        String invalidBase64 = "InvalidBase64";
        assertNull(Codec.base64ToStr(invalidBase64));
    }

    /**
     * Test for base64ToStr method with null input.
     * This test checks if the method returns null when input is null.
     */
    @Test
    public void testBase64ToStr_NullInput() {
        assertNull(Codec.base64ToStr(null));
    }

    /**
     * Test for bytesToBase64DataUrl method with valid inputs.
     * This test checks if the method correctly creates a Base64 Data URL string.
     */
    @Test
    public void testBytesToBase64DataUrl_ValidInput() {
        byte[] input = "Hello".getBytes();
        String expected = "data:image/png;base64,SGVsbG8"; // Assuming type is "png"
        assertEquals(expected, Codec.bytesToBase64DataUrl(input, "png"));
    }

    // Helper method to create a mock MultipartFile
    private MultipartFile createMockMultipartFile(String content) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getInputStream()).thenReturn(inputStream);
        Mockito.when(file.getOriginalFilename()).thenReturn("test.txt");
        return file;
    }
}
!!!!test_end!!!!
