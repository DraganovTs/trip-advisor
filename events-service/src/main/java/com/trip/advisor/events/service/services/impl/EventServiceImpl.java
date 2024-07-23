package com.trip.advisor.events.service.services.impl;

import com.trip.advisor.events.service.exception.EventAlreadyExistException;
import com.trip.advisor.events.service.mapper.EventMapper;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.repository.EventRepository;
import com.trip.advisor.events.service.services.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository) {
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
        return false;
    }

    /**
     * update event
     *
     * @param eventDTO
     * @return
     */
    @Override
    public boolean updateEvent(EventDTO eventDTO) {
        return false;
    }

    /**
     * get events by date
     *
     * @param dateTime
     * @return
     */
    @Override
    public List<EventDTO> getEventsByDate(LocalDateTime dateTime) {
        return List.of();
    }

    /**
     * get all events by city name
     *
     * @param city
     * @return
     */
    @Override
    public List<EventDTO> getAllEventsInCity(String city) {
        return List.of();
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
    public List<EventDTO> getAllEventByCityAndTimePeriod(String city, LocalDateTime startDate, LocalDateTime endDate) {
        return List.of();
    }
}
