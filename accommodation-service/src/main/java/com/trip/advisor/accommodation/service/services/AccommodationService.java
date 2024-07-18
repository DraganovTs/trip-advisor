package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

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
     * @param accommodationDTO updated accommodation
     * @return AccommodationDTO
     */
    boolean updateAccommodation( AccommodationDTO accommodationDTO);

    /**
     *
     * @param name
     * @param city
     * @param street
     * @return boolean is deleted or not
     */
    @Transactional
    @Modifying
    boolean deleteAccommodation(String name,String city, String street);
}
