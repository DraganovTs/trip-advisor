package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;

public interface AccommodationService {

    /**
     *
     * @param accommodationDTO - accommodationDTO Object
     */
    void createAccommodation(AccommodationDTO accommodationDTO);
}
