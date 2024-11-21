package com.trip.advisor.reservation.service.service.impl;

import com.trip.advisor.reservation.service.model.entity.ReservationHistory;
import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
import com.trip.advisor.reservation.service.service.ReservationHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationHistoryServiceImpl implements ReservationHistoryService {
    @Override
    public void add(UUID reservationId, ReservationStatus reservationStatus) {

    }

    @Override
    public List<ReservationHistory> getReservationHistory(UUID reservationId) {
        return List.of();
    }
}
