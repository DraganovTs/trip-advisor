package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;

import java.util.List;

public interface AccommodationService {

    /**
     *
     * @param accommodationDTO - accommodationDTO Object
     */
    void createAccommodation(AccommodationDTO accommodationDTO);

    /**
     *
     * @return all accommodations ind AccommodationDTO type
     */
    List<AccommodationDTO> getAllAccommodations();

    /**
     *
     * @param name of accommodation
     * @return AccommodationDTO
     */
    AccommodationDTO getAccommodationByName(String name);

    /**
     *
     * @param id accommodation id
     * @param accommodationDTO updated accommodation
     * @return AccommodationDTO
     */
    AccommodationDTO updateAccommodation(long id, AccommodationDTO accommodationDTO);
    void deleteAccommodation(long id);
}
