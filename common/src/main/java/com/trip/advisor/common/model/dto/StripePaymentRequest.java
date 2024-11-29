package com.trip.advisor.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripePaymentRequest {
    private BigDecimal amount;
    private Long quantity;
    private String name;
    private String currency;
}
