package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.repository.AccommodationRepository;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import com.trip.advisor.accommodation.service.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ReservationService reservationService;

    public AccommodationServiceImpl(AccommodationRepository accommodationRepository, ReservationService reservationService) {
        this.accommodationRepository = accommodationRepository;
        this.reservationService = reservationService;
    }

    /**
     * @param accommodationDTO - accommodationDTO Object
     */
    @Override
    public void createAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = AccommodationMapper.mapAccommodationDTOToAccommodation(accommodationDTO);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        List<Reservation> reservations =
                reservationService.initializeReservation(accommodationDTO.getReservations()
                        , accommodation.getAccommodationId());
        if (accommodationDTO.getReservations() != null) {
            savedAccommodation.setReservations(reservations);
            accommodationRepository.save(savedAccommodation);
        }
    }

    /**
     * @return all accommodations ind AccommodationDTO type
     */
    @Override
    public List<AccommodationDTO> getAllAccommodations() {
        return List.of();
    }

    /**
     * @param id of accommodation
     * @return AccommodationDTO
     */
    @Override
    public AccommodationDTO getAccommodationById(long id) {
        return null;
    }

    /**
     * @param id               accommodation id
     * @param accommodationDTO updated accommodation
     * @return AccommodationDTO
     */
    @Override
    public AccommodationDTO updateAccommodation(long id, AccommodationDTO accommodationDTO) {
        return null;
    }

    @Override
    public void deleteAccommodation(long id) {

    }
}
