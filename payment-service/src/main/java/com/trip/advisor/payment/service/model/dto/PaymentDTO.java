package com.trip.advisor.payment.service.model.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private UUID id;
    @NotNull(message = "reservation id must not be null!")
    private UUID reservationId;
    @NotNull(message = "accommodation id must be not null!")
    private UUID accommodationId;
    @NotNull(message = "price must be not null!")
    private BigDecimal price;
    @NotNull(message = "accommodation name must not be null!")
    private String accommodationName;
    @NotNull(message = "user must not be null!")
    private UUID userId;
}
