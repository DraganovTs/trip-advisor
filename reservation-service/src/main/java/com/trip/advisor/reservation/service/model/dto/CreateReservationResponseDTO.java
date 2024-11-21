package com.trip.advisor.reservation.service.model.dto;

import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
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
