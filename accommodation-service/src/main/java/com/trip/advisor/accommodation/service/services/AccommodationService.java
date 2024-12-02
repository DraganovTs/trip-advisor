package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.common.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
     * @param type
     * @return list of accommodations
     */
    List<AccommodationDTO> getAccommodationsByType(String type);

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

    Accommodation getAccommodationById(UUID accommodationId);

    AccommodationDTO getAccommodationDTOById(UUID id);
}
