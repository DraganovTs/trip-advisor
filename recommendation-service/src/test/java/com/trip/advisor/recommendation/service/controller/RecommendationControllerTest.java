package com.trip.advisor.recommendation.service.controller;

import com.trip.advisor.recommendation.service.constants.RecommendationConstants;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationServiceImpl recommendationService;


    @Test
    public void testCreateRecommendation() throws Exception {
        RecommendationDTO recommendationDTO = new RecommendationDTO("Central Park", "Sightseeing", "New York", null, null, null, null, null, null, null);

        doNothing().when(recommendationService).createRecommendation(any(RecommendationDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Central Park\", \"type\": \"Sightseeing\", \"city\": \"New York\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(RecommendationConstants.STATUS_201)))
                .andExpect(jsonPath("$.statusMessage", is(RecommendationConstants.MESSAGE_201)));
    }

    @Test
    public void testUpdateRecommendation_Success() throws Exception {
        when(recommendationService.updateRecommendation(any(RecommendationDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/recommendation/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Central Park\", \"type\": \"Sightseeing\", \"city\": \"New York\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("200")))
                .andExpect(jsonPath("$.statusMessage", is("Request processed successfully")));
    }

    @Test
    public void testUpdateRecommendation_Failure() throws Exception {
        when(recommendationService.updateRecommendation(any(RecommendationDTO.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/recommendation/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Central Park\", \"type\": \"Sightseeing\", \"city\": \"New York\" }"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode", is("500")))
                .andExpect(jsonPath("$.statusMessage", is("An error occurred.Please try again or contact Dev team")));
    }

    @Test
    public void testFetchRecommendationByCity() throws Exception {
        RecommendationDTO recommendationDTO = new RecommendationDTO("Central Park", "Sightseeing", "New York", null, null, null, null, null, null, null);
        List<RecommendationDTO> recommendationDTOList = Arrays.asList(recommendationDTO);

        when(recommendationService.getRecommendationByCity("New York")).thenReturn(recommendationDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendation/fetch/New York"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Central Park")))
                .andExpect(jsonPath("$[0].type", is("Sightseeing")))
                .andExpect(jsonPath("$[0].city", is("New York")));
    }

    @Test
    public void testFetchRecommendationByCityAndType() throws Exception {
        RecommendationDTO recommendationDTO = new RecommendationDTO("Central Park", "Sightseeing", "New York", null, null, null, null, null, null, null);
        List<RecommendationDTO> recommendationDTOList = Arrays.asList(recommendationDTO);

        when(recommendationService.getRecommendationByCityAndType("New York", "Sightseeing")).thenReturn(recommendationDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendation/fetch/cityAndType")
                        .param("city", "New York")
                        .param("type", "Sightseeing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Central Park")))
                .andExpect(jsonPath("$[0].type", is("Sightseeing")))
                .andExpect(jsonPath("$[0].city", is("New York")));
    }

    @Test
    public void testDeleteRecommendationByCityAndName_Success() throws Exception {
        when(recommendationService.deleteByCityAndName("New York", "Central Park")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/recommendation/delete")
                        .param("city", "New York")
                        .param("name", "Central Park"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("200")))
                .andExpect(jsonPath("$.statusMessage", is("Request processed successfully")));
    }

    @Test
    public void testDeleteRecommendationByCityAndName_Failure() throws Exception {
        when(recommendationService.deleteByCityAndName("New York", "Central Park")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/recommendation/delete")
                        .param("city", "New York")
                        .param("name", "Central Park"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode", is("500")))
                .andExpect(jsonPath("$.statusMessage", is("An error occurred.Please try again or contact Dev team")));
    }
}
