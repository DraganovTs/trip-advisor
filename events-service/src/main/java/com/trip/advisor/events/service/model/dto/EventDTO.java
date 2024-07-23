package com.trip.advisor.events.service.model.dto;


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


    @Size(min = 1, max = 30)
    @NotEmpty(message = "Name is required!")
    private String name;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "City is required!")
    private String city;
    @NotEmpty(message = "Type is required")
    private String eventType;
    @Future(message = "Event must be not in past")
    private LocalDate date;
    @Positive(message = "price must be positive")
    private double ticketPrice;
}
