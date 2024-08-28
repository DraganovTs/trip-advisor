package com.trip.advisor.events.service.controller;

import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.events.service.constants.EventConstants;
import com.trip.advisor.events.service.constants.EventMessage;
import com.trip.advisor.events.service.model.dto.EventDTO;
import com.trip.advisor.events.service.model.dto.EventsContactInfoDTO;
import com.trip.advisor.events.service.services.impl.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "CRUD REST API for Events in TripAdvisor",
        description = "CRUD REST APIs in TripAdvisor to CREATE,UPDATE,FETCH AND DELETE events details"
)
@RestController
@RequestMapping(value = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class EventController {

    private final EventServiceImpl eventService;
    @Value("${build.version}")
    private String buildVersion;
    private final Environment environment;
    private final EventsContactInfoDTO eventsContactInfoDTO;

    public EventController(EventServiceImpl eventService, Environment environment,
                           EventsContactInfoDTO eventsContactInfoDTO) {
        this.eventService = eventService;
        this.environment = environment;
        this.eventsContactInfoDTO = eventsContactInfoDTO;
    }

    @Operation(
            summary = "Create event REST API",
            description = "REST API to create event in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
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

    @Operation(
            summary = "Update event REST API",
            description = "REST API to update an event in TripAdvisor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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

    @Operation(
            summary = "Delete event REST API",
            description = "REST API to delete an event in TripAdvisor by name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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

    @Operation(
            summary = "Get events by date REST API",
            description = "REST API to fetch events in TripAdvisor by date"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/getByDate/{date}")
    public ResponseEntity<List<EventDTO>> getEventsByDate(@PathVariable(value = "date")
                                                          @Future(message = EventMessage.EVENT_DATE_FUTURE)

                                                          LocalDate date) {
        List<EventDTO> events = eventService.getEventsByDate(date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(events);
    }

    @Operation(
            summary = "Get events by city REST API",
            description = "REST API to fetch events in TripAdvisor by city"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
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

    @Operation(
            summary = "Get events by city and time period REST API",
            description = "REST API to fetch events in TripAdvisor by city and time period"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
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

    @GetMapping("/build-info")
    public ResponseEntity<String>getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )

    })
    @GetMapping("/contact-info")
    public ResponseEntity<EventsContactInfoDTO> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventsContactInfoDTO);
    }


}
