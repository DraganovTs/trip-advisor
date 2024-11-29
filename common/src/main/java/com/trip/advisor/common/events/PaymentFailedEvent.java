package com.trip.advisor.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailedEvent {
    private UUID reservationId;
    private UUID accommodationId;
    private String accommodationName;

}
