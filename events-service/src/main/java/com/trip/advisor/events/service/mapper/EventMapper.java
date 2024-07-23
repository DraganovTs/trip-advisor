package com.trip.advisor.events.service.mapper;

import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.entity.Event;
import com.trip.advisor.events.service.model.enums.Type;
import org.springframework.stereotype.Component;

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
}
