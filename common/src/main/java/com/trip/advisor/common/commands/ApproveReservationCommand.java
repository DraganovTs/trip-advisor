package com.trip.advisor.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveReservationCommand {
    private UUID reservationId;
    private UUID accommodationId;
    private UUID userId;
}
