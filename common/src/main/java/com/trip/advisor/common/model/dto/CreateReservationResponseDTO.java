package com.trip.advisor.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReservationResponseDTO {
    private UUID reservationId;
    private UUID userId;
    private UUID accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
}
