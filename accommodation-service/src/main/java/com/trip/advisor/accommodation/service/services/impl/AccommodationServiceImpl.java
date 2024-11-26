package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.exception.AccommodationAlreadyExistException;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import com.trip.advisor.common.exception.ResourceNotFoundException;
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
import java.util.UUID;
import java.util.stream.Collectors;

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
            throw new AccommodationAlreadyExistException("Accommodation already exists with given name and address: "
                    + String.join(", ", accommodation.getName(), accommodation.getAddress().toString()));
        }

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        List<Reservation> reservations = reservationService.initializeReservation(accommodationDTO.getReservations(),
                savedAccommodation.getAccommodationId());
        for (Reservation reservation : reservations) {
            savedAccommodation.addReservation(reservation); // Add reservations to the accommodation
        }

        accommodationRepository.save(savedAccommodation); // Save the accommodation with reservations
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
        Accommodation accommodation = accommodationRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Accommodation", "Accommodation name", name));

        return AccommodationMapper.mapAccommodationToAccommodationDTO(accommodation);
    }

    /**
     * @param type
     * @return list of accommodations
     */
    @Override
    public List<AccommodationDTO> getAccommodationsByType(String type) {
        try {
            AccommodationType accommodationType = AccommodationType.valueOf(type.toUpperCase());

            List<Accommodation> accommodations = accommodationRepository.findByType(accommodationType)
                    .orElseThrow(() -> new ResourceNotFoundException("Accommodation", "Accommodation type", type));

            return accommodations.stream()
                    .map(AccommodationMapper::mapAccommodationToAccommodationDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Accommodation", "Accommodation type", type);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }


    /**
     * @param accommodationDTO updated accommodation
     * @return boolean indicating success
     */
    @Override
    public boolean updateAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation existingAccommodation = accommodationRepository.findByNameAndAddress_CityAndAddress_Street(
                accommodationDTO.getName(),
                accommodationDTO.getAddress().getCity(),
                accommodationDTO.getAddress().getStreet()
        ).orElseThrow(
                () -> new ResourceNotFoundException("Accommodation",
                        "accommodation name, accommodation city, accommodation street",
                        String.join(", ", accommodationDTO.getName(), accommodationDTO.getAddress().toString())
                ));

        Accommodation updatedAccommodation = AccommodationMapper.mapAccommodationDTOToAccommodation(accommodationDTO);
        updatedAccommodation.setAccommodationId(existingAccommodation.getAccommodationId());
        accommodationRepository.save(updatedAccommodation);

        return true;
    }

    /**
     * @param name accommodation name
     * @param city accommodation city
     * @param street accommodation street
     * @return boolean indicating if deletion was successful
     */
    @Override
    public boolean deleteAccommodation(String name, String city, String street) {
        Accommodation accommodation = accommodationRepository.findByNameAndAddress_CityAndAddress_Street(
                        name, city, street)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Accommodation",
                                "accommodation name, accommodation city, accommodation street",
                                String.join(", ", name, city, street)
                        ));
        reservationService.deleteByAccommodationId(accommodation.getAccommodationId());
        accommodationRepository.deleteAccommodationByAccommodationId(accommodation.getAccommodationId());

        return true;
    }

    @Override
    public Accommodation getAccommodationById(UUID accommodationId) {
        return accommodationRepository.findByAccommodationId(accommodationId)
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation", "accommodationId",
                        accommodationId.toString()));
    }


}
