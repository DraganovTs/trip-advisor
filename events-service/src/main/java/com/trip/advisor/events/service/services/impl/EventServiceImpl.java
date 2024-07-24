package com.trip.advisor.events.service.services.impl;

import com.trip.advisor.common.exception.ResourceNotFoundException;
import com.trip.advisor.events.service.exception.EventAlreadyExistException;
import com.trip.advisor.events.service.mapper.EventMapper;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.repository.EventRepository;
import com.trip.advisor.events.service.services.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * create new event
     *
     * @param eventDTO
     */
    @Override
    public void createEvent(EventDTO eventDTO) {
        Event event = EventMapper.mapEventDTOToEvent(eventDTO);
        Optional<Event> optionalEvent = eventRepository.findByName(event.getName());
        if (optionalEvent.isPresent()) {
            throw new EventAlreadyExistException("Event already exist whit given name: " + event.getName());
        }

        eventRepository.save(event);
    }

    /**
     * delete event by its name
     *
     * @param name
     * @return
     */
    @Override
    public boolean deleteEvent(String name) {
        Event event = eventRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Event", "event name", name));

        eventRepository.delete(event);

        return true;
    }

    /**
     * update event
     *
     * @param eventDTO
     * @return
     */
    @Override
    public boolean updateEvent(EventDTO eventDTO) {
        Event event = eventRepository.findByName(eventDTO.getName()).orElseThrow(
                () -> new ResourceNotFoundException("Event", "event name", eventDTO.getName()));

        Event updatedEvent = EventMapper.mapEventDTOToEvent(eventDTO);
        updatedEvent.setId(event.getId());
        eventRepository.save(updatedEvent);

        return true;
    }

    /**
     * get events by date
     *
     * @param date
     * @return
     */
    @Override
    public List<EventDTO> getEventsByDate(LocalDate date) {
        List<Event> eventsOnDate = eventRepository.findAllByDate(date).orElseThrow(
                () -> new ResourceNotFoundException("Event", "event date", date.toString())
        );
        return EventMapper.mapEventToEventDTO(eventsOnDate);
    }

    /**
     * get all events by city name
     *
     * @param city
     * @return
     */
    @Override
    public List<EventDTO> getAllEventsInCity(String city) {
        List<Event> eventsInCity = eventRepository.findAllByCity(city).orElseThrow(
                () -> new ResourceNotFoundException("Event", "City", city));
        return EventMapper.mapEventToEventDTO(eventsInCity);
    }

    /**
     * get all events in city by time period
     *
     * @param city
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<EventDTO> getAllEventByCityAndTimePeriod(String city, LocalDate startDate, LocalDate endDate) {
        List<Event> eventsByCityAndDateBetween = eventRepository.findAllByCityAndDateBetween(city, startDate, endDate)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Event", "City , Start date , End date",
                                String.join(", ", city, startDate.toString(), endDate.toString())));

        return EventMapper.mapEventToEventDTO(eventsByCityAndDateBetween);
    }
}
