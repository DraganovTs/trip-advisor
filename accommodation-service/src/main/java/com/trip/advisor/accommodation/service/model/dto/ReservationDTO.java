package com.trip.advisor.accommodation.service.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ReservationDTO {

    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    @Size(min = 3, max = 20)
    private String guestName;
    @NotNull
    @Email
    private String guestEmail;
}
