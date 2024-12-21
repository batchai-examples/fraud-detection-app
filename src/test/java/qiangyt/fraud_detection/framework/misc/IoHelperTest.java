package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Test class for IoHelper.
 */
public class IoHelperTest {

    /**
     * Test case for getting file extension from a valid file name.
     * Expected: "txt" for "document.txt"
     */
    @Test
    public void testGetFileExtension_ValidFileName() {
        String fileName = "document.txt";
        String expectedExtension = "txt";
        String actualExtension = IoHelper.getFileExtension(fileName);
        assertEquals(expectedExtension, actualExtension);
    }

    /**
     * Test case for getting file extension from a file name without extension.
     * Expected: "" for "document"
     */
    @Test
    public void testGetFileExtension_NoExtension() {
        String fileName = "document";
        String expectedExtension = "";
        String actualExtension = IoHelper.getFileExtension(fileName);
        assertEquals(expectedExtension, actualExtension);
    }

    /**
     * Test case for getting file extension from a file name with multiple dots.
     * Expected: "jpg" for "image.tar.jpg"
     */
    @Test
    public void testGetFileExtension_MultipleDots() {
        String fileName = "image.tar.jpg";
        String expectedExtension = "jpg";
        String actualExtension = IoHelper.getFileExtension(fileName);
        assertEquals(expectedExtension, actualExtension);
    }

    /**
     * Test case for getting file extension from a File object.
     * Expected: "pdf" for a File object with name "report.pdf"
     */
    @Test
    public void testGetFileExtension_FileObject() {
        File file = new File("report.pdf");
        String expectedExtension = "pdf";
        String actualExtension = IoHelper.getFileExtension(file);
        assertEquals(expectedExtension, actualExtension);
    }

    /**
     * Test case for reading fully from an InputStream.
     * Expected: byte array containing "Hello, World!"
     */
    @Test
    public void testReadFully_ValidInputStream() throws IOException {
        String inputString = "Hello, World!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        byte[] expectedOutput = inputString.getBytes();
        byte[] actualOutput = IoHelper.readFully(inputStream);
        assertArrayEquals(expectedOutput, actualOutput);
    }

    /**
     * Test case for reading from an InputStream that throws an IOException.
     * Expected: Internal exception should be thrown.
     */
    @Test
    public void testReadFully_InputStreamThrowsIOException() {
        InputStream inputStream = new ByteArrayInputStream(new byte[0]) {
            @Override
            public int read(byte[] b) throws IOException {
                throw new IOException("Simulated IOException");
            }
        };
        assertThrows(Internal.class, () -> IoHelper.readFully(inputStream));
    }
}
