package com.trip.advisor.accommodation.service.services.impl;

import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.AddressDTO;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.repository.AddressRepository;
import com.trip.advisor.accommodation.service.services.AddressService;

public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AccommodationMapper accommodationMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AccommodationMapper accommodationMapper) {
        this.addressRepository = addressRepository;
        this.accommodationMapper = accommodationMapper;
    }

    /**
     * @param addressDTO - create and save address from addressDTO
     */
    @Override
    public void createAddress(AddressDTO addressDTO) {
        Address address = AccommodationMapper.mapToAddress(addressDTO);
        addressRepository.save(address);
    }
}
