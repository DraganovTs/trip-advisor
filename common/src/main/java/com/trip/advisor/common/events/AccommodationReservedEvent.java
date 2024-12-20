package com.trip.advisor.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReservedEvent {
    private UUID reservationId;
    private UUID userId;
    private UUID accommodationId;
    private BigDecimal price;
    private String accommodationName;
    private LocalDate startDate;
    private LocalDate endDate;


}
