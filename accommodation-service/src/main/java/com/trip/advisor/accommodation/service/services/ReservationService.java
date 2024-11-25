package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationService {

    /**
     *
     * @param reservationDTO create accommodation entity
     */
    Reservation createReservation(ReservationDTO reservationDTO);

    /**
     *
     * @return All ReservationDTOs
     */
    List<ReservationDTO> getAllReservations();

    /**
     *
     * @param id of reservation
     * @return ReservationDTO
     */
    ReservationDTO getReservationById(long id);

    /**
     *
     * @param id of reservation
     * @param reservationDTO updated ReservationDTO
     * @return ReservationDTO
     */
    ReservationDTO updateReservation(long id, ReservationDTO reservationDTO);
    void deleteReservation(long id);

    /**
     * @param reservations list of initial reservations
     * @return List of reservationDTOs
     */
    List<Reservation> initializeReservation(List<ReservationDTO> reservations,UUID accommodationId);

    /**
     *
     * @param accommodationId
     */
    void deleteByAccommodationId(UUID accommodationId);

    boolean checkIfIsAlreadyReserved(LocalDate startDate, LocalDate endDate, UUID accommodationId);
}
