package com.trip.advisor.accommodation.service.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class ReservationDTO {

    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotEmpty(message = "Guest name must be not empty")
    @Size(min = 3, max = 20)
    private String guestName;
    @NotEmpty(message = "Email must be not empty")
    @Email
    private String guestEmail;
}
