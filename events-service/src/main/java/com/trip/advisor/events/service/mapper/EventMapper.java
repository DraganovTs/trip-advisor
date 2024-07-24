package com.trip.advisor.events.service.mapper;

import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.model.enums.Type;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    public static Event mapEventDTOToEvent(EventDTO eventDTO) {
        return Event.builder()
                .name(eventDTO.getName())
                .city(eventDTO.getCity())
                .eventType(Type.valueOf(eventDTO.getEventType()))
                .date(eventDTO.getDate())
                .ticketPrice(eventDTO.getTicketPrice())
                .build();
    }

    public static List<EventDTO> mapEventToEventDTO(List<Event> eventsOnDate) {
        return eventsOnDate.stream().map(event ->
                EventDTO.builder()
                        .name(event.getName())
                        .city(event.getCity())
                        .date(event.getDate())
                        .ticketPrice(event.getTicketPrice())
                        .build()
        ).collect(Collectors.toList());
    }
}
