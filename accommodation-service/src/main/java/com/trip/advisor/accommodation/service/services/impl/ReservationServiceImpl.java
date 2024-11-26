package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.exception.ReservationOverlapping;
import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.repository.ReservationRepository;
import com.trip.advisor.accommodation.service.services.ReservationService;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import com.trip.advisor.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * @param reservationDTO create reservation entity
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
        List<Reservation> reservations = reservationRepository.findAll();
        return AccommodationMapper.mapReservationsToReservationDTOs(reservations);
    }

    /**
     * @param id of reservation
     * @return ReservationDTO
     */
    @Override
    public ReservationDTO getReservationById(long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", String.valueOf(id)));
        return AccommodationMapper.mapReservationToReservationDTO(reservation);
    }

    /**
     * @param id             of reservation
     * @param reservationDTO updated ReservationDTO
     * @return ReservationDTO
     */
    @Override
    public ReservationDTO updateReservation(long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", String.valueOf(id)));

        Reservation updatedReservation = AccommodationMapper.mapReservationDTOToReservation(reservationDTO);
        updatedReservation.setId(id);

        reservationRepository.save(updatedReservation);

        return AccommodationMapper.mapReservationToReservationDTO(updatedReservation);
    }


    @Override
    public void deleteReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", "id", String.valueOf(id));
        }
        reservationRepository.deleteById(id);
    }

    /**
     * @param reservations list of initial reservations
     * @param accommodationId ID of the associated accommodation
     * @return List of reservation entities
     */
    @Override
    public List<Reservation> initializeReservation(List<ReservationDTO> reservations, UUID accommodationId) {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }

        List<Reservation> reservationList = new ArrayList<>();
        for (ReservationDTO reservationDTO : reservations) {
            Reservation currentReservation = AccommodationMapper.mapReservationDTOToReservation(reservationDTO);
            currentReservation.setAccommodationId(accommodationId);
            reservationList.add(currentReservation);
        }

        return reservationRepository.saveAll(reservationList);
    }

    /**
     * @param accommodationId ID of the associated accommodation
     */
    @Override
    public void deleteByAccommodationId(UUID accommodationId) {
        reservationRepository.deleteReservationByAccommodationId(accommodationId);
    }

    @Override
    public void checkIfIsAlreadyReserved(LocalDate startDate, LocalDate endDate, UUID accommodationId) {
        boolean present = reservationRepository.findOverlappingReservation(startDate, endDate, accommodationId).isPresent();
        if (present) throw new ReservationOverlapping("Accommodation already reserved for this dates");
    }

    @Override
    public Reservation createReservationFromCommand(ReserveAccommodationCommand command) {
        Reservation reservationToSave = AccommodationMapper.mapReserveAccommodationCommandToReservation(command);
        return reservationRepository.save(reservationToSave);
    }
}
