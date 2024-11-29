package com.trip.advisor.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelAccommodationReservationCommand {
    private UUID accommodationId;
    private UUID reservationId;
    private LocalDate startDate;
    private LocalDate endDate;

}
