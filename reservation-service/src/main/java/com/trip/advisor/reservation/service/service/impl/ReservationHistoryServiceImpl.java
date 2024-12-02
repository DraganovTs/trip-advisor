package com.trip.advisor.reservation.service.service.impl;

import com.trip.advisor.reservation.service.model.entity.ReservationHistory;
import com.trip.advisor.common.model.dto.ReservationStatus;
import com.trip.advisor.reservation.service.repository.ReservationHistoryRepository;
import com.trip.advisor.reservation.service.service.ReservationHistoryService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationHistoryServiceImpl implements ReservationHistoryService {

    private final ReservationHistoryRepository reservationHistoryRepository;

    public ReservationHistoryServiceImpl(ReservationHistoryRepository reservationHistoryRepository) {
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

    @Override
    public void add(UUID reservationId, ReservationStatus reservationStatus) {
        ReservationHistory reservationHistory = ReservationHistory.builder()
                .reservationId(reservationId)
                .reservationStatus(reservationStatus)
                .createdAt(new Timestamp(new Date().getTime()))
                .build();
        reservationHistoryRepository.save(reservationHistory);
    }

    @Override
    public List<ReservationHistory> getReservationHistory() {
        return reservationHistoryRepository.findAll();
    }
}
