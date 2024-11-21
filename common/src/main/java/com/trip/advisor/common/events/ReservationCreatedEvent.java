package com.trip.advisor.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreatedEvent {
    private UUID reservationId;
    private UUID userId;
    private UUID accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
