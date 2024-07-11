package com.trip.advisor.accommodation.service.mapper;

import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.dto.AddressDTO;
import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccommodationMapper {


    public static Accommodation mapAccommodationDTOToAccommodation(AccommodationDTO accommodationDTO) {
        Address address = mapToAddress(accommodationDTO.getAddress());

        return Accommodation.builder()
                .name(accommodationDTO.getName())
                .price(accommodationDTO.getPrice())
                .type(AccommodationType.valueOf(accommodationDTO.getType()))
                .rating(accommodationDTO.getRating())
                .available(true)
                .address(address)
                .reservations(new ArrayList<>())
                .build();
    }

    public static Reservation mapReservationDTOToReservation(ReservationDTO reservationDTO) {
        return Reservation.builder()
                .startDate(reservationDTO.getStartDate())
                .endDate(reservationDTO.getEndDate())
                .guestName(reservationDTO.getGuestName())
                .guestEmail(reservationDTO.getGuestEmail())
                .build();
    }

    private static AddressDTO mapToAddressDTO(AddressDTO addressDTO) {
        return AddressDTO.builder()
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }

    private static Address mapToAddress(AddressDTO addressDTO) {
        return Address.builder()
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }

}
