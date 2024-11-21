package com.trip.advisor.reservation.service.service.impl;

import com.trip.advisor.common.events.ReservationCreatedEvent;
import com.trip.advisor.reservation.service.mapper.ReservationMapper;
import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.reservation.service.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.reservation.service.model.entity.Reservation;
import com.trip.advisor.reservation.service.repository.ReservationRepository;
import com.trip.advisor.reservation.service.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public CreateReservationResponseDTO placeReservation(CreateReservationRequestDTO reservationRequestDTO) {
        var reservation = reservationMapper.mapReservationRequestToReservation(reservationRequestDTO);
        Reservation savedReservation = reservationRepository.save(reservation);

        ReservationCreatedEvent placeReservation = new ReservationCreatedEvent(
                savedReservation.getReservationId(),
                savedReservation.getUserId(),
                savedReservation.getAccommodationId(),
                savedReservation.getStartDate(),
                savedReservation.getEndDate()
        );

        return reservationMapper.mapReservationToReservationResponse(savedReservation);
    }

    @Override
    public void approveReservation(UUID reservationId) {

    }

    @Override
    public void rejectReservation(UUID reservationId) {

    }
}
