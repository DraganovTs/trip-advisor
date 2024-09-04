package com.trip.advisor.events.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.dto.EventsContactInfoDTO;
import com.trip.advisor.events.service.services.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventServiceImpl eventService;
    @Autowired
    private ObjectMapper objectMapper;

    private EventDTO eventDTO;

    @BeforeEach
    void setUp() {
        eventDTO = EventDTO.builder()
                .name("Test Event")
                .city("Test City")
                .eventType("CONCERT")
                .date(LocalDate.now().plusDays(3L))
                .ticketPrice(250.00)
                .build();
    }

    @Test
    void testCreateEvent() throws Exception {
        Mockito.doNothing().when(eventService).createEvent(any(EventDTO.class));

        mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMessage").value("Event created successfully"));
    }

    @Test
    void testUpdateEvent() throws Exception {
        Mockito.when(eventService.updateEvent(any(EventDTO.class))).thenReturn(true);

        mockMvc.perform(put("/api/events/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.statusMessage").value("Request processed successfully"));
    }

    @Test
    void testDeleteEvent() throws Exception {
        String eventName = "Test Event";
        Mockito.when(eventService.deleteEvent(eventName)).thenReturn(true);

        mockMvc.perform(delete("/api/events/delete/{name}", eventName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.statusMessage").value("Request processed successfully"));
    }


    @Test
    void testGetEventsByDate() throws Exception {
        LocalDate date = LocalDate.now().plusDays(3L);
        Mockito.when(eventService.getEventsByDate(date)).thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(get("/api/events/getByDate/{date}", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(eventDTO.getName()))
                .andExpect(jsonPath("$[0].city").value(eventDTO.getCity()));
    }


    @Test
    void testGetAllEventsInCity() throws Exception {
        String city = "Test City";
        Mockito.when(eventService.getAllEventsInCity(city)).thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(get("/api/events/getByCity/{city}", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(eventDTO.getName()))
                .andExpect(jsonPath("$[0].city").value(eventDTO.getCity()));
    }


    @Test
    void testGetAllEventByCityAndTimePeriod() throws Exception {
        String city = "Test City";
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);
        Mockito.when(eventService.getAllEventByCityAndTimePeriod(city, startDate, endDate))
                .thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(get("/api/events/getByCityAndTime")
                        .param("city", city)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(eventDTO.getName()))
                .andExpect(jsonPath("$[0].city").value(eventDTO.getCity()));
    }


    @Test
    void testGetBuildVersion() throws Exception {
        mockMvc.perform(get("/api/events/build-info"))
                .andExpect(status().isOk())
                .andExpect(content().string("2.0")); // Assuming build version is 2.0
    }

    @Test
    void testGetJavaVersion() throws Exception {
        mockMvc.perform(get("/api/events/java-version"))
                .andExpect(status().isOk());
    }

    @Test
    void getContactInfo_Success() throws Exception {

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/contact-info")
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
