package com.trip.advisor.events.service.services;

import com.trip.advisor.events.service.model.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    /**
     * create new event
     * @param eventDTO
     */
    void createEvent(EventDTO eventDTO);

    /**
     * delete event by its name
     * @param name
     * @return
     */
    boolean deleteEvent(String name);

    /**
     * update event
     * @param eventDTO
     * @return
     */
    boolean updateEvent(EventDTO eventDTO);

    /**
     * get events by date
     * @param dateTime
     * @return
     */
    List<EventDTO> getEventsByDate(LocalDateTime dateTime);

    /**
     * get all events by city name
     * @param city
     * @return
     */
    List<EventDTO> getAllEventsInCity(String city);

    /**
     * get all events in city by time period
     * @param city
     * @param startDate
     * @param endDate
     * @return
     */
    List<EventDTO> getAllEventByCityAndTimePeriod(String city, LocalDateTime startDate, LocalDateTime endDate);

}
