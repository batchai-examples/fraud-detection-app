package qiangyt.fraud_detection.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DetectionRestController.class)
public class DetectionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DetectionService service;

    @InjectMocks
    private DetectionRestController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case for submitting a detection request successfully.
     * This is a happy path test where the request is valid and processed correctly.
     */
    @Test
    public void testSubmitDetectionRequest_Success() throws Exception {
        DetectionReq req = new DetectionReq(); // Assume this is a valid request
        DetectionReqEntity expectedResponse = new DetectionReqEntity(); // Assume this is a valid response

        when(service.submit(any(DetectionReq.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.someField").value(expectedResponse.getSomeField())); // Adjust based on actual fields
    }

    /**
     * Test case for submitting a detection request with invalid data.
     * This is a negative case where the request does not meet validation criteria.
     */
    @Test
    public void testSubmitDetectionRequest_InvalidData() throws Exception {
        DetectionReq req = new DetectionReq(); // Assume this is an invalid request

        mockMvc.perform(post("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case for synchronous detection processing successfully.
     * This is a happy path test where the request entity is valid and processed correctly.
     */
    @Test
    public void testDetect_Success() throws Exception {
        DetectionReqEntity entity = new DetectionReqEntity(); // Assume this is a valid entity
        DetectionResult expectedResult = new DetectionResult(); // Assume this is a valid result

        when(service.detect(any(DetectionReqEntity.class))).thenReturn(expectedResult);

        mockMvc.perform(get("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultField").value(expectedResult.getResultField())); // Adjust based on actual fields
    }

    /**
     * Test case for synchronous detection processing with invalid data.
     * This is a negative case where the request entity does not meet validation criteria.
     */
    @Test
    public void testDetect_InvalidData() throws Exception {
        DetectionReqEntity entity = new DetectionReqEntity(); // Assume this is an invalid entity

        mockMvc.perform(get("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case for synchronous detection processing with missing request body.
     * This tests the corner case where no data is sent in the request.
     */
    @Test
    public void testDetect_MissingRequestBody() throws Exception {
        mockMvc.perform(get("/rest/detection")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
