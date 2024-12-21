package qiangyt.fraud_detection.framework.misc;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for StringHelper utility methods.
 */
public class StringHelperTest {

    /**
     * Test case for parsePhoneNumber with a valid phone number.
     * It should return a Pair containing the country code and phone number.
     */
    @Test
    public void testParsePhoneNumber_ValidInput() {
        // Given a valid phone number with country code
        String input = "+1234567890";
        
        // When parsing the phone number
        Pair<String, String> result = StringHelper.parsePhoneNumber(input);
        
        // Then the result should contain the correct country code and phone number
        assertNotNull(result);
        assertEquals("123", result.getLeft());
        assertEquals("4567890", result.getRight());
    }

    /**
     * Test case for parsePhoneNumber with an empty string.
     * It should return null.
     */
    @Test
    public void testParsePhoneNumber_EmptyInput() {
        // Given an empty input
        String input = "";
        
        // When parsing the phone number
        Pair<String, String> result = StringHelper.parsePhoneNumber(input);
        
        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for parsePhoneNumber with a null input.
     * It should return null.
     */
    @Test
    public void testParsePhoneNumber_NullInput() {
        // Given a null input
        String input = null;
        
        // When parsing the phone number
        Pair<String, String> result = StringHelper.parsePhoneNumber(input);
        
        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for parsePhoneNumber with an invalid phone number format.
     * It should return null.
     */
    @Test
    public void testParsePhoneNumber_InvalidFormat() {
        // Given an invalid phone number
        String input = "+123abc456";
        
        // When parsing the phone number
        Pair<String, String> result = StringHelper.parsePhoneNumber(input);
        
        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for toString with a valid array.
     * It should return a string representation of the array.
     */
    @Test
    public void testToString_ValidArray() {
        // Given a valid array
        String[] array = {"one", "two", "three"};
        
        // When converting to string
        String result = StringHelper.toString(array);
        
        // Then the result should match the expected string representation
        assertEquals("[one, two, three]", result);
    }

    /**
     * Test case for toString with a null array.
     * It should return null.
     */
    @Test
    public void testToString_NullArray() {
        // Given a null array
        String[] array = null;
        
        // When converting to string
        String result = StringHelper.toString(array);
        
        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for join with a valid collection.
     * It should return a concatenated string with the specified delimiter.
     */
    @Test
    public void testJoin_ValidCollection() {
        // Given a valid collection of strings
        Collection<String> texts = Arrays.asList("apple", "banana", "cherry");
        
        // When joining with a delimiter
        String result = StringHelper.join(", ", texts);
        
        // Then the result should match the expected concatenated string
        assertEquals("apple, banana, cherry", result);
    }

    /**
     * Test case for join with a null collection.
     * It should return null.
     */
    @Test
    public void testJoin_NullCollection() {
        // Given a null collection
        Collection<String> texts = null;
        
        // When joining with a delimiter
        String result = StringHelper.join(", ", texts);
        
        // Then the result should be null
        assertNull(result);
    }

    /**
     * Test case for right with a valid string and length.
     * It should return the rightmost part of the string.
     */
    @Test
    public void testRight_ValidInput() {
        // Given a valid string and length
        String input = "abcdef";
        int length = 3;
        
        // When getting the rightmost part
        String result = StringHelper.right(input, length);
        
        // Then the result should match the expected substring
        assertEquals("def", result);
    }

    /**
     * Test case for right with a string shorter than the specified length.
     * It should return the original string.
     */
    @Test
    public void testRight_ShortString() {
        // Given a short string
        String input = "ab";
        int length = 5;
        
        // When getting the rightmost part
        String result = StringHelper.right(input, length);
        
        // Then the result should be the original string
        assertEquals("ab", result);
    }

    /**
     * Test case for left with a valid string and length.
     * It should return the leftmost part of the string.
     */
    @Test
    public void testLeft_ValidInput() {
        // Given a valid string and length
        String input = "abcdef";
        int length = 3;
        
        // When getting the leftmost part
        String result = StringHelper.left(input, length);
        
        // Then the result should match the expected substring
        assertEquals("abc", result);
    }

    /**
     * Test case for left with a string shorter than the specified length.
     * It should return the original string.
     */
    @Test
    public void testLeft_ShortString() {
        // Given a short string
        String input = "ab";
        int length = 5;
        
        // When getting the leftmost part
        String result = StringHelper.left(input, length);
        
        // Then the result should be the original string
        assertEquals("ab", result);
    }
}
