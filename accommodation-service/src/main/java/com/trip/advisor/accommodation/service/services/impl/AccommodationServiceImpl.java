package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.repository.AccommodationRepository;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceImpl implements AccommodationService {

   private final AccommodationRepository accommodationRepository;

    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    /**
     * @param accommodationDTO - accommodationDTO Object
     */
    @Override
    public void createAccommodation(AccommodationDTO accommodationDTO) {

    }
}
