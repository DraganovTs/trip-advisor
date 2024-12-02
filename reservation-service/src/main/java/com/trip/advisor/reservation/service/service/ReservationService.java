package com.trip.advisor.reservation.service.service;

import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.common.model.dto.CreateReservationResponseDTO;

import java.util.UUID;

public interface ReservationService {

    CreateReservationResponseDTO placeReservation(CreateReservationRequestDTO reservationRequestDTO);

    void approveReservation(UUID reservationId);

    void rejectReservation(UUID reservationId);

    CreateReservationResponseDTO getReservationById(UUID reservationId);
}
