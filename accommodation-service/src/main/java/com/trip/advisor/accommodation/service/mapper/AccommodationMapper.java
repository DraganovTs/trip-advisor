package com.trip.advisor.accommodation.service.mapper;

import com.trip.advisor.accommodation.service.model.dto.AddressDTO;
import com.trip.advisor.accommodation.service.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AccommodationMapper {

    public static AddressDTO mapToAddressDTO( AddressDTO addressDTO) {
        return AddressDTO.builder()
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }

    public static Address mapToAddress(AddressDTO addressDTO){
        return Address.builder()
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }


}
