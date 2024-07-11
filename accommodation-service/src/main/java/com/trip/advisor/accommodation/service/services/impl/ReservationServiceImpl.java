package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.repository.ReservationRepository;
import com.trip.advisor.accommodation.service.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * @param reservationDTO create accommodation entity
     */
    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = AccommodationMapper.mapReservationDTOToReservation(reservationDTO);
        return reservationRepository.save(reservation);
    }

    /**
     * @return All ReservationDTOs
     */
    @Override
    public List<ReservationDTO> getAllReservations() {
        return List.of();
    }

    /**
     * @param id of reservation
     * @return ReservationDTO
     */
    @Override
    public ReservationDTO getReservationById(long id) {
        return null;
    }

    /**
     * @param id             of reservation
     * @param reservationDTO updated ReservationDTO
     * @return ReservationDTO
     */
    @Override
    public ReservationDTO updateReservation(long id, ReservationDTO reservationDTO) {
        return null;
    }

    @Override
    public void deleteReservation(long id) {

    }

    /**
     * @param reservations list of initial reservations
     * @return List of reservationDTOs
     */
    @Override
    public List<Reservation> initializeReservation(List<ReservationDTO> reservations,Long accommodationId) {
        if (reservations.isEmpty()) {
            return new ArrayList<>();
        }
        List<Reservation> reservationList = new ArrayList<>();
        for (ReservationDTO reservationDTO : reservations) {
            Reservation currentReservation = AccommodationMapper.mapReservationDTOToReservation(reservationDTO);
            currentReservation.setAccommodationId(accommodationId);
        }
        return reservationRepository.saveAll(reservationList);
    }
}
