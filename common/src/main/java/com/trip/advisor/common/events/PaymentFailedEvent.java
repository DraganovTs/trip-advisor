package com.trip.advisor.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailedEvent {
    private UUID reservationId;
    private UUID accommodationId;
    private UUID userId;
    private String accommodationName;
    private LocalDate startDate;
    private LocalDate endDate;

}
