package com.trip.advisor.reservation.service.repository;

import com.trip.advisor.reservation.service.model.entity.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, UUID> {
    List<ReservationHistory> findByReservationId(UUID reservationId);
}
