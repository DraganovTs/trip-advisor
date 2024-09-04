package com.trip.advisor.recommendation.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.recommendation.service.constants.RecommendationConstants;
import com.trip.advisor.recommendation.service.exception.RecommendationAlreadyExist;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationServiceImpl recommendationService;
    @Autowired
    private ObjectMapper objectMapper;

    private RecommendationDTO recommendationDTO;

    @BeforeEach
    public void setUp() {

        recommendationDTO = RecommendationDTO.builder()
                .name("Test Recommendation")
                .type("CAFE")
                .city("Test City")
                .address("Test Address")
                .description("Test Description")
                .rating(5.00)
                .website("Test Website")
                .contactNumber("885504")
                .email("Test@Email.com")
                .openingHours("00-13")
                .build();

    }

    @Test
    public void testCreateRecommendation_Success() throws Exception {
        // Arrange

        // Act
        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(RecommendationConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMessage").value(RecommendationConstants.MESSAGE_201));
    }

    @Test
    public void testCreateRecommendation_Failure() throws Exception {
        // Arrange
        doThrow(new RecommendationAlreadyExist("Recommendation already exists")).when(recommendationService)
                .createRecommendation(any(RecommendationDTO.class));

        // Act
        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendationDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(RecommendationConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMessage").value(RecommendationConstants.MESSAGE_201));
    }

    @Test
    public void testUpdateRecommendation_Success() throws Exception {
        // Arrange
        when(recommendationService.updateRecommendation(any(RecommendationDTO.class))).thenReturn(true);

        // Act
        mockMvc.perform(put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(Constants.STATUS_200))
                .andExpect(jsonPath("$.statusMessage").value(Constants.MESSAGE_200));
    }

    @Test
    public void testUpdateRecommendation_Failure() throws Exception {
        // Arrange
        when(recommendationService.updateRecommendation(any(RecommendationDTO.class))).thenReturn(false);

        // Act
        mockMvc.perform(put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendationDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(Constants.STATUS_500))
                .andExpect(jsonPath("$.statusMessage").value(Constants.MESSAGE_500));
    }

    @Test
    public void testFetchRecommendationByCity_Success() throws Exception {
        // Arrange
        List<RecommendationDTO> recommendations = Collections.singletonList(recommendationDTO);
        when(recommendationService.getRecommendationByCity("Test City")).thenReturn(recommendations);

        // Act
        mockMvc.perform(get("/api/fetch/{city}", "Test City")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Recommendation"))
                .andExpect(jsonPath("$[0].type").value("CAFE"));
    }

    @Test
    public void testFetchRecommendationByCity_NotFound() throws Exception {
        // Arrange
        when(recommendationService.getRecommendationByCity("Test City"))
                .thenThrow(new ResourceNotFoundException("Recommendation", "City", "Test City"));

        // Act
        mockMvc.perform(get("/api/fetch/{city}", "Test City")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value("404"))
                .andExpect(jsonPath("$.statusMessage").value("Recommendation not found for city: Test City"));
    }


    @Test
    public void testFetchRecommendationByCityAndType_Success() throws Exception {
        // Arrange
        List<RecommendationDTO> recommendations = Collections.singletonList(recommendationDTO);
        when(recommendationService.getRecommendationByCityAndType("Test City", "CAFE")).thenReturn(recommendations);

        // Act
        mockMvc.perform(get("/api/fetch/cityAndType")
                        .param("city", "Test City")
                        .param("type", "CAFE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Recommendation"))
                .andExpect(jsonPath("$[0].type").value("CAFE"));
    }

    @Test
    public void testFetchRecommendationByCityAndType_NotFound() throws Exception {
        // Arrange
        when(recommendationService.getRecommendationByCityAndType("Test City", "CAFE"))
                .thenThrow(new ResourceNotFoundException("Recommendation", "City and Type", "Test City CAFE"));

        // Act
        mockMvc.perform(get("/api/fetch/cityAndType")
                        .param("city", "Test City")
                        .param("type", "CAFE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value("404"))
                .andExpect(jsonPath("$.statusMessage").value("Recommendation not found for city: Test City and type: CAFE"));
    }


    @Test
    public void testDeleteRecommendationByCityAndName_Success() throws Exception {
        // Arrange
        when(recommendationService.deleteByCityAndName("Test City", "Test Recommendation")).thenReturn(true);

        // Act
        mockMvc.perform(delete("/api/delete")
                        .param("city", "Test City")
                        .param("name", "Test Recommendation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(Constants.STATUS_200))
                .andExpect(jsonPath("$.statusMessage").value(Constants.MESSAGE_200));
    }

    @Test
    public void testDeleteRecommendationByCityAndName_NotFound() throws Exception {
        // Arrange
        when(recommendationService.deleteByCityAndName("Test City", "Test Recommendation")).thenReturn(false);

        // Act
        mockMvc.perform(delete("/api/delete")
                        .param("city", "Test City")
                        .param("name", "Test Recommendation"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(Constants.STATUS_500))
                .andExpect(jsonPath("$.statusMessage").value(Constants.MESSAGE_500));
    }

    @Test
    void testGetBuildVersion() throws Exception {
        mockMvc.perform(get("/api/recommendation/build-info"))
                .andExpect(status().isOk())
                .andExpect(content().string("2.0")); // Assuming build version is 2.0
    }

    @Test
    void testGetJavaVersion() throws Exception {
        mockMvc.perform(get("/api/recommendation/java-version"))
                .andExpect(status().isOk());
    }


    @Test
    void getContactInfo_Success() throws Exception {

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendation/contact-info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Welcome to tripadvisor events related Docker APIs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactDetails.name")
                        .value("Mr Tze"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactDetails.email")
                        .value("tze@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.onCallSupport[0]")
                        .value("-(888)888-888 -(777)777-777"))
                .andDo(MockMvcResultHandlers.print());
    }


}
