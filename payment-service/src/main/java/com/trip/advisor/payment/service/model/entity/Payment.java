package com.trip.advisor.payment.service.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "reservation_id")
    private UUID reservationId;
    @Column(name = "accommodation_id")
    private UUID accommodationId;
    @Column(name = "accommodation_price")
    private BigDecimal price;

}
