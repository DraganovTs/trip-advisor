package com.trip.advisor.reservation.service.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReservationRequestDTO {
    @NotNull(message = "User ID cannot be null. Please provide a valid User ID.")
    private UUID userId;

    @NotNull(message = "Accommodation ID cannot be null. Please provide a valid Accommodation ID.")
    private UUID accommodationId;

    @FutureOrPresent(message = "Start date must be today or a future date.")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date must be today or a future date.")
    private LocalDate endDate;
}