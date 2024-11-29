package com.trip.advisor.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveAccommodationCommand {
    private UUID accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID reservationId;
    private String userName;
    private String userEmail;

}
