package com.trip.advisor.reservation.service.service;

import com.trip.advisor.reservation.service.model.entity.ReservationHistory;
import com.trip.advisor.common.model.dto.ReservationStatus;

import java.util.List;
import java.util.UUID;

public interface ReservationHistoryService {
    void add(UUID reservationId, ReservationStatus reservationStatus);

    List<ReservationHistory> getReservationHistory();
}
