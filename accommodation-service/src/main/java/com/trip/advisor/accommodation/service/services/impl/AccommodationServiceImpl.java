package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.exception.AccommodationAlreadyExistException;
import com.trip.advisor.accommodation.service.exception.ResourceNotFoundException;
import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.repository.AccommodationRepository;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import com.trip.advisor.accommodation.service.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Accommodation> optionalAccommodation = accommodationRepository.findByNameAndAddress_CityAndAddress_Street(
                accommodation.getName(),
                accommodation.getAddress().getCity(),
                accommodation.getAddress().getStreet()
        );
        if (optionalAccommodation.isPresent()) {
            throw new AccommodationAlreadyExistException("Accommodation already exist whit given name and address: "
                    + String.join(", ", accommodation.getName(), accommodation.getAddress().toString()));
        }
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
     * @param name of accommodation
     * @return AccommodationDTO
     */
    @Override
    public AccommodationDTO getAccommodationByName(String name) {
        Accommodation optionalAccommodation = accommodationRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Accommodation"
                        , "AccommodationId", name));

        return AccommodationMapper.mapAccommodationToAccommodationDTO(optionalAccommodation);
    }

    /**
     * @param accommodationDTO updated accommodation
     * @return AccommodationDTO
     */
    @Override
    public boolean updateAccommodation(AccommodationDTO accommodationDTO) {
        Optional<Accommodation> optionalAccommodation = Optional.ofNullable(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(
                accommodationDTO.getName(),
                accommodationDTO.getAddress().getCity(),
                accommodationDTO.getAddress().getStreet()
        ).orElseThrow(
                () -> new ResourceNotFoundException("Accommodation",
                        "accommodation name,accommodation city, accommodation street",
                        String.join(", ", accommodationDTO.getName(),
                                accommodationDTO.getAddress().toString())
                )));
        Accommodation updatedAccommodation = AccommodationMapper.mapAccommodationDTOToAccommodation(accommodationDTO);
        updatedAccommodation.setAccommodationId(optionalAccommodation.get().getAccommodationId());
        updatedAccommodation.setReservations(optionalAccommodation.get().getReservations());
        accommodationRepository.save(updatedAccommodation);

        return true;
    }

    @Override
    public void deleteAccommodation(long id) {
        accommodationRepository.deleteById(id);
    }
}
