package com.trip.advisor.accommodation.service.services;

import com.trip.advisor.accommodation.service.model.dto.AddressDTO;

public interface AddressService {

    /**
     *
     * @param addressDTO - create and save address from addressDTO
     */
    void createAddress(AddressDTO addressDTO);
}
