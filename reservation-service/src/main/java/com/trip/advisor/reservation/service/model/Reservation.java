package com.trip.advisor.reservation.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private UUID reservationId;
    private UUID userId;
    private UUID vehicleId;
}
