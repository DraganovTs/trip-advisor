package com.trip.advisor.reservation.service.model.entity;

import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "reservation_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(name = "reservationId")
    private UUID reservationId;
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
