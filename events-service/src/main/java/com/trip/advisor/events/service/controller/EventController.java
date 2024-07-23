package com.trip.advisor.events.service.controller;

import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.events.service.constants.EventConstants;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.services.EventService;
import com.trip.advisor.events.service.services.impl.EventServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EventController {

    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEvent(@RequestBody EventDTO eventDTO) {

        eventService.createEvent(eventDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        EventConstants.STATUS_201,
                        EventConstants.MESSAGE_201
                ));
    }
}
