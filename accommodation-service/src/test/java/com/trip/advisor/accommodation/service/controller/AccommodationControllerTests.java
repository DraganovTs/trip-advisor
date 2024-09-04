package com.trip.advisor.accommodation.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.advisor.accommodation.service.constants.AccommodationConstants;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.dto.AddressDTO;
import com.trip.advisor.accommodation.service.services.impl.AccommodationServiceImpl;
import com.trip.advisor.common.constants.Constants;
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


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AccommodationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccommodationServiceImpl accommodationService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createAccommodation_Success() throws Exception {
        // Arrange
        AddressDTO addressDTO = AddressDTO.builder()
                .street("Vitoshka")
                .country("Bulgaria")
                .state("Sofia")
                .city("Sofia")
                .postalCode("1000")
                .build();

        AccommodationDTO accommodationDTO =AccommodationDTO.builder()
                .name("Test")
                .price(15.00)
                .type("HOTEL")
                .rating(5.00)
                .address(addressDTO)
                .reservations(new ArrayList<>())
                .build();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(AccommodationConstants.STATUS_201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(AccommodationConstants.MESSAGE_201))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).createAccommodation(accommodationDTO);
    }


    @Test
    void fetchAccommodation_Success() throws Exception {
        // Arrange
        String name = "Test";
        AccommodationDTO accommodationDTO = AccommodationDTO.builder()
                .name(name)
                .price(15.00)
                .type("HOTEL")
                .rating(5.00)
                .address(new AddressDTO("Vitoshka", "Sofia", "Sofia", "Bulgaria", "1000"))
                .build();

        when(accommodationService.getAccommodationByName(name)).thenReturn(accommodationDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fetch")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(15.00))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).getAccommodationByName(name);
    }

    @Test
    void fetchAllByType_Success() throws Exception {
        // Arrange
        String type = "HOTEL";
        List<AccommodationDTO> accommodationDTOList = new ArrayList<>();
        accommodationDTOList.add(AccommodationDTO.builder()
                .name("Test")
                .price(15.00)
                .type(type)
                .rating(5.00)
                .address(new AddressDTO("Vitoshka", "Sofia", "Sofia", "Bulgaria", "1000"))
                .build());

        when(accommodationService.getAccommodationsByType(type)).thenReturn(accommodationDTOList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fetchAllByType/{type}", type)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value(type))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test"))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).getAccommodationsByType(type);
    }

    @Test
    void updateAccommodation_Success() throws Exception {
        // Arrange
        AccommodationDTO accommodationDTO = AccommodationDTO.builder()
                .name("Test")
                .price(20.00)
                .type("HOTEL")
                .rating(4.50)
                .address(new AddressDTO("Vitoshka", "Sofia", "Sofia", "Bulgaria", "1000"))
                .build();

        when(accommodationService.updateAccommodation(accommodationDTO)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(Constants.MESSAGE_200))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).updateAccommodation(accommodationDTO);
    }

    @Test
    void deleteAccommodation_Success() throws Exception {
        // Arrange
        String name = "Test";
        String city = "Sofia";
        String street = "Vitoshka";

        when(accommodationService.deleteAccommodation(name, city, street)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                        .param("name", name)
                        .param("city", city)
                        .param("street", street)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(Constants.MESSAGE_200))
                .andDo(MockMvcResultHandlers.print());

        // Verify service interaction
        verify(accommodationService).deleteAccommodation(name, city, street);
    }

    @Test
    void getBuildVersion_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/build-info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getJavaVersion_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/java-version")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getContactInfo_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contact-info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Welcome to tripadvisor accommodations related Docker APIs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactDetails.name")
                        .value("Mr Tze"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactDetails.email")
                        .value("tze@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.onCallSupport[0]")
                        .value("-(888)888-888 -(777)777-777"))
                .andDo(MockMvcResultHandlers.print());
    }


}
