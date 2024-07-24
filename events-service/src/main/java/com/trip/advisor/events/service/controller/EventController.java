package com.trip.advisor.events.service.controller;

import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.events.service.constants.EventConstants;
import com.trip.advisor.events.service.constants.EventMessage;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.services.impl.EventServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class EventController {

    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {

        eventService.createEvent(eventDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        EventConstants.STATUS_201,
                        EventConstants.MESSAGE_201
                ));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateEvent(@Valid @RequestBody
                                                   EventDTO eventDTO) {
        boolean success = eventService.updateEvent(eventDTO);
        if (success) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500, Constants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ResponseDTO> deleteEvent(@PathVariable
                                                   @Size(min = 1, max = 30, message = EventMessage.EVENT_NAME_SIZE)
                                                   @NotEmpty(message = EventMessage.EVENT_NAME_REQUIRED)
                                                   String name) {
        boolean isDeleted = eventService.deleteEvent(name);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500, Constants.MESSAGE_500));
        }
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<List<EventDTO>> getEventsByDate(@PathVariable(value = "date")
                                                          @Future(message = EventMessage.EVENT_DATE_FUTURE)

                                                          LocalDate date) {
        List<EventDTO> events = eventService.getEventsByDate(date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(events);
    }

    @GetMapping("/getByCity/{city}")
    public ResponseEntity<List<EventDTO>> getAllEventsInCity(@PathVariable(value = "city")
                                                             @Size(min = 2, max = 20,
                                                                     message = EventMessage.CITY_NAME_SIZE)
                                                             @NotEmpty(message = EventMessage.CITY_NAME_REQUIRED)
                                                             String city) {
        List<EventDTO> events = eventService.getAllEventsInCity(city);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(events);
    }

    @GetMapping("/getByCityAndTime")
    public ResponseEntity<List<EventDTO>> getAllEventsInCity(
            @Size(min = 2, max = 20, message = EventMessage.CITY_NAME_SIZE)
            @NotEmpty(message = EventMessage.CITY_NAME_REQUIRED)
            @RequestParam String city,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        List<EventDTO> events = eventService.getAllEventByCityAndTimePeriod(city, startDate, endDate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(events);
    }
}
