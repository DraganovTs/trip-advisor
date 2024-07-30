package com.trip.advisor.events.service.model.dto;


import com.trip.advisor.events.service.constants.EventMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "EventDTO",
        description = "Schema to hold event information"
)
public class EventDTO {


    @Schema(
            description = "Name of the event",
            example = "Music Concert"
    )
    @Size(min = 1, max = 30, message = EventMessage.EVENT_NAME_SIZE)
    @NotEmpty(message = EventMessage.EVENT_NAME_REQUIRED)
    private String name;

    @Schema(
            description = "City where the event is held",
            example = "New York"
    )
    @Size(min = 2, max = 20, message = EventMessage.CITY_NAME_SIZE)
    @NotEmpty(message = EventMessage.CITY_NAME_REQUIRED)
    private String city;

    @Schema(
            description = "Type of the event (e.g., concert, conference)",
            example = "Concert"
    )
    @NotEmpty(message = EventMessage.EVENT_TYPE_REQUIRED)
    private String eventType;

    @Schema(
            description = "Date of the event",
            example = "2024-08-15"
    )
    @Future(message = EventMessage.EVENT_DATE_FUTURE)
    private LocalDate date;

    @Schema(
            description = "Price of the event ticket",
            example = "50.0"
    )
    @Positive(message = EventMessage.EVENT_PRICE_POSITIVE)
    private double ticketPrice;
}
