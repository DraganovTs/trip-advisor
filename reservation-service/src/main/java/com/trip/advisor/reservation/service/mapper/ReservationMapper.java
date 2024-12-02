package com.trip.advisor.reservation.service.mapper;

import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.common.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.reservation.service.model.entity.Reservation;
import com.trip.advisor.common.model.dto.ReservationStatus;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation mapReservationRequestToReservation(CreateReservationRequestDTO reservationRequestDTO) {
        return Reservation.builder()
                .userId(reservationRequestDTO.getUserId())
                .accommodationId(reservationRequestDTO.getAccommodationId())
                .startDate(reservationRequestDTO.getStartDate())
                .endDate(reservationRequestDTO.getEndDate())
                .reservationStatus(ReservationStatus.CREATED)
                .guestName(reservationRequestDTO.getUserName())
                .guestEmail(reservationRequestDTO.getUserEmail())
                .build();
    }

    public CreateReservationResponseDTO mapReservationToReservationResponse(Reservation reservation) {
        return CreateReservationResponseDTO.builder()
                .reservationId(reservation.getReservationId())
                .userId(reservation.getUserId())
                .accommodationId(reservation.getAccommodationId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getReservationStatus())
                .build();
    }
}
