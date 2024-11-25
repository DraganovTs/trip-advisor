package com.trip.advisor.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReservedEvent {
    private UUID accommodationId;
    private double price;
}