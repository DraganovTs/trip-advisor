package com.trip.advisor.accommodation.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.advisor.accommodation.service.constants.AccommodationConstants;
import com.trip.advisor.accommodation.service.model.dto.AccommodationContactInfoDTO;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.*;

@WebMvcTest(controllers = AccommodationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AccommodationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccommodationService accommodationService;
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void createAccommodation_Success() throws Exception {
        // Arrange
        AccommodationDTO accommodationDTO = new AccommodationDTO();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(AccommodationConstants.STATUS_201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(AccommodationConstants.MESSAGE_201))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).createAccommodation(accommodationDTO);
    }









}
