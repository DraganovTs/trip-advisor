package com.trip.advisor.events.service.service;

import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.events.service.exception.EventAlreadyExistException;
import com.trip.advisor.events.service.mapper.EventMapper;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.model.enums.Type;
import com.trip.advisor.events.service.repository.EventRepository;
import com.trip.advisor.events.service.services.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private EventDTO eventDTO;
    private Event event;

    @BeforeEach
    void setUp() {
        eventDTO = EventDTO.builder()
                .name("Test Event")
                .city("Test City")
                .eventType("CONCERT")
                .date(LocalDate.now().plusDays(3L))
                .ticketPrice(250.00)
                .build();

        event = Event.builder()
                .name("Test Event")
                .city("Test City")
                .eventType(Type.CONCERT)
                .date(LocalDate.now().plusDays(3L))
                .ticketPrice(250.00)
                .build();
    }

    @Test
    void createEvent_WhenEventDoesNotExist_ShouldCreateEvent() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.empty());
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        eventService.createEvent(eventDTO);

        // Assert
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEvent_WhenEventAlreadyExists_ShouldThrowException() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.of(event));

        // Act & Assert
        assertThrows(EventAlreadyExistException.class, () -> eventService.createEvent(eventDTO));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void deleteEvent_WhenEventExists_ShouldDeleteEvent() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.of(event));

        // Act
        boolean isDeleted = eventService.deleteEvent(eventDTO.getName());

        // Assert
        assertTrue(isDeleted);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void deleteEvent_WhenEventDoesNotExist_ShouldThrowException() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.deleteEvent(eventDTO.getName()));
        verify(eventRepository, never()).delete(any(Event.class));
    }

    @Test
    void updateEvent_WhenEventExists_ShouldUpdateEvent() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        boolean isUpdated = eventService.updateEvent(eventDTO);

        // Assert
        assertTrue(isUpdated);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void updateEvent_WhenEventDoesNotExist_ShouldThrowException() {
        // Arrange
        when(eventRepository.findByName(eventDTO.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.updateEvent(eventDTO));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void getEventsByDate_WhenEventsExist_ShouldReturnEventDTOList() {
        // Arrange
        when(eventRepository.findAllByDate(eventDTO.getDate())).thenReturn(Optional.of(List.of(event)));

        // Act
        List<EventDTO> events = eventService.getEventsByDate(eventDTO.getDate());

        // Assert
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(eventDTO.getName(), events.get(0).getName());
    }

    @Test
    void getEventsByDate_WhenEventsDoNotExist_ShouldThrowException() {
        // Arrange
        when(eventRepository.findAllByDate(eventDTO.getDate())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventsByDate(eventDTO.getDate()));
    }

    @Test
    void getAllEventsInCity_WhenEventsExist_ShouldReturnEventDTOList() {
        // Arrange
        when(eventRepository.findAllByCity(eventDTO.getCity())).thenReturn(Optional.of(List.of(event)));

        // Act
        List<EventDTO> events = eventService.getAllEventsInCity(eventDTO.getCity());

        // Assert
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(eventDTO.getCity(), events.get(0).getCity());
    }

    @Test
    void getAllEventsInCity_WhenEventsDoNotExist_ShouldThrowException() {
        // Arrange
        when(eventRepository.findAllByCity(eventDTO.getCity())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.getAllEventsInCity(eventDTO.getCity()));
    }

    @Test
    void getAllEventByCityAndTimePeriod_WhenEventsExist_ShouldReturnEventDTOList() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(1L);
        LocalDate endDate = LocalDate.now().plusDays(5L);
        when(eventRepository.findAllByCityAndDateBetween(eventDTO.getCity(), startDate, endDate))
                .thenReturn(Optional.of(List.of(event)));

        // Act
        List<EventDTO> events = eventService.getAllEventByCityAndTimePeriod(eventDTO.getCity(), startDate, endDate);

        // Assert
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(eventDTO.getCity(), events.get(0).getCity());
    }

    @Test
    void getAllEventByCityAndTimePeriod_WhenEventsDoNotExist_ShouldThrowException() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(1L);
        LocalDate endDate = LocalDate.now().plusDays(5L);
        when(eventRepository.findAllByCityAndDateBetween(eventDTO.getCity(), startDate, endDate))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.getAllEventByCityAndTimePeriod(eventDTO.getCity(), startDate, endDate));
    }
}
