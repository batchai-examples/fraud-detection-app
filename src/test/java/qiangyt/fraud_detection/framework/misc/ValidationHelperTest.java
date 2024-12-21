package qiangyt.fraud_detection.framework.misc;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationHelperTest {

    /**
     * Test for hackBean method with a valid ObjectError containing a ConstraintViolation.
     * It should return the leaf bean of the violation.
     */
    @Test
    public void testHackBean_ValidObjectError_ReturnsLeafBean() {
        // Arrange
        ObjectError error = mock(ObjectError.class);
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        Object leafBean = new Object();
        
        when(error.unwrap(ConstraintViolation.class)).thenReturn(violation);
        when(violation.getLeafBean()).thenReturn(leafBean);
        
        // Act
        Object result = ValidationHelper.hackBean(error);
        
        // Assert
        assertEquals(leafBean, result);
    }

    /**
     * Test for hackBean method with an ObjectError that does not contain a ConstraintViolation.
     * It should return null.
     */
    @Test
    public void testHackBean_InvalidObjectError_ReturnsNull() {
        // Arrange
        ObjectError error = mock(ObjectError.class);
        
        when(error.unwrap(ConstraintViolation.class)).thenReturn(null);
        
        // Act
        Object result = ValidationHelper.hackBean(error);
        
        // Assert
        assertNull(result);
    }

    /**
     * Test for hackBeanClass method with a valid ObjectError.
     * It should return the class of the leaf bean.
     */
    @Test
    public void testHackBeanClass_ValidObjectError_ReturnsLeafBeanClass() {
        // Arrange
        ObjectError error = mock(ObjectError.class);
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        Object leafBean = new TestBean();
        
        when(error.unwrap(ConstraintViolation.class)).thenReturn(violation);
        when(violation.getLeafBean()).thenReturn(leafBean);
        
        // Act
        Class<?> result = ValidationHelper.hackBeanClass(error);
        
        // Assert
        assertEquals(TestBean.class, result);
    }

    /**
     * Test for hackBeanClass method with an ObjectError that does not contain a ConstraintViolation.
     * It should return null.
     */
    @Test
    public void testHackBeanClass_InvalidObjectError_ReturnsNull() {
        // Arrange
        ObjectError error = mock(ObjectError.class);
        
        when(error.unwrap(ConstraintViolation.class)).thenReturn(null);
        
        // Act
        Class<?> result = ValidationHelper.hackBeanClass(error);
        
        // Assert
        assertNull(result);
    }

    /**
     * Test for hackJsonFieldName method with a valid FieldError.
     * It should return the JSON field name if the JsonProperty annotation is present.
     */
    @Test
    public void testHackJsonFieldName_ValidFieldError_ReturnsJsonFieldName() {
        // Arrange
        FieldError error = mock(FieldError.class);
        when(error.getField()).thenReturn("name");
        
        Class<?> klass = TestBean.class;
        when(error.unwrap(ConstraintViolation.class)).thenReturn(null);
        
        // Act
        String result = ValidationHelper.hackJsonFieldName(error);
        
        // Assert
        assertEquals("jsonName", result);
    }

    /**
     * Test for hackJsonFieldName method with a FieldError that does not have a JsonProperty annotation.
     * It should return the original field name.
     */
    @Test
    public void testHackJsonFieldName_NoJsonProperty_ReturnsOriginalFieldName() {
        // Arrange
        FieldError error = mock(FieldError.class);
        when(error.getField()).thenReturn("unknownField");
        
        // Act
        String result = ValidationHelper.hackJsonFieldName(error);
        
        // Assert
        assertEquals("unknownField", result);
    }

    // Test bean class for the purpose of testing
    public static class TestBean {
        @JsonProperty("jsonName")
        private String name;
    }
}
