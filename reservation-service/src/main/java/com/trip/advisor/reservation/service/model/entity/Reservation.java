package com.trip.advisor.reservation.service.model.entity;

import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reservationId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "accommodation_id", nullable = false)
    private UUID accommodationId;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
}
