package com.trip.advisor.events.service.repository;


import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.model.enums.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EventRepositoryTests {


    @Autowired
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    public void setUp() {

        eventRepository.deleteAll();

        event = Event.builder()
                .name("Test Event")
                .city("Test City")
                .eventType(Type.CONCERT)
                .date(LocalDate.now().plusDays(2L))
                .ticketPrice(200.00)
                .build();
        eventRepository.save(event);
    }

    @Test
    public void EventsRepository_Save_ReturnSavedEvent() {
        Event savedEvent = eventRepository.save(event);

        Assertions.assertNotNull(savedEvent);
        Assertions.assertEquals(event.getName(), savedEvent.getName());
        Assertions.assertEquals(event.getCity(), savedEvent.getCity());
    }

    @Test
    public void EventsRepository_FindByName_ReturnEvent() {
        Optional<Event> foundEvent = eventRepository.findByName(event.getName());

        Assertions.assertTrue(foundEvent.isPresent());
        Assertions.assertEquals(event.getName(), foundEvent.get().getName());
    }

    @Test
    public void EventsRepository_FindAllByDate_ReturnEventsList() {
        LocalDate eventDate = event.getDate();
        Optional<List<Event>> foundEvents = eventRepository.findAllByDate(eventDate);

        Assertions.assertTrue(foundEvents.isPresent());
        Assertions.assertFalse(foundEvents.get().isEmpty());
        Assertions.assertEquals(1, foundEvents.get().size());
        Assertions.assertEquals(eventDate, foundEvents.get().get(0).getDate());
    }

    @Test
    public void EventsRepository_FindAllByCity_ReturnEventsList() {
        Optional<List<Event>> foundEvents = eventRepository.findAllByCity(event.getCity());

        Assertions.assertTrue(foundEvents.isPresent());
        Assertions.assertFalse(foundEvents.get().isEmpty());
        Assertions.assertEquals(1, foundEvents.get().size());
        Assertions.assertEquals(event.getCity(), foundEvents.get().get(0).getCity());
    }

    @Test
    public void EventsRepository_FindAllByCityAndDateBetween_ReturnEventsList() {
        LocalDate startDate = LocalDate.now().plusDays(1L);
        LocalDate endDate = LocalDate.now().plusDays(3L);

        Optional<List<Event>> foundEvents = eventRepository.findAllByCityAndDateBetween(event.getCity(), startDate, endDate);

        Assertions.assertTrue(foundEvents.isPresent());
        Assertions.assertFalse(foundEvents.get().isEmpty());
        Assertions.assertEquals(1, foundEvents.get().size());
        Assertions.assertEquals(event.getCity(), foundEvents.get().get(0).getCity());
        Assertions.assertTrue(
                foundEvents.get().get(0).getDate().isAfter(startDate.minusDays(1)) &&
                        foundEvents.get().get(0).getDate().isBefore(endDate.plusDays(1))
        );
    }

}
