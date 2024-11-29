package com.trip.advisor.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentCommand {
    private UUID reservationId;
    private UUID accommodationId;
    private BigDecimal reservationPrice;
    private String accommodationName;
}
