package com.trip.advisor.events.service.model.dto;


import com.trip.advisor.events.service.constants.EventMessage;
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
public class EventDTO {


    @Size(min = 1, max = 30,message = EventMessage.EVENT_NAME_SIZE)
    @NotEmpty(message = EventMessage.EVENT_NAME_REQUIRED)
    private String name;
    @Size(min = 2,max = 20, message = EventMessage.CITY_NAME_SIZE)
    @NotEmpty(message = EventMessage.CITY_NAME_REQUIRED)
    private String city;
    @NotEmpty(message = EventMessage.EVENT_TYPE_REQUIRED)
    private String eventType;
    @Future(message = EventMessage.EVENT_DATE_FUTURE)
    private LocalDate date;
    @Positive(message = EventMessage.EVENT_PRICE_POSITIVE)
    private double ticketPrice;
}
