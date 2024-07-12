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
import java.util.List;

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

    public static AccommodationDTO mapAccommodationToAccommodationDTO(Accommodation optionalAccommodation) {
        return AccommodationDTO.builder()
                .name(optionalAccommodation.getName())
                .price(optionalAccommodation.getPrice())
                .type(optionalAccommodation.getType().toString())
                .rating(optionalAccommodation.getRating())
                .address(mapToAddressDTO(optionalAccommodation.getAddress()))
                .reservations(mapReservationToReservationDTO(optionalAccommodation.getReservations()))
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

    public static List<ReservationDTO> mapReservationToReservationDTO(List<Reservation> reservations) {
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationDTO current = ReservationDTO.builder()
                    .startDate(reservation.getStartDate())
                    .endDate(reservation.getEndDate())
                    .guestName(reservation.getGuestName())
                    .guestEmail(reservation.getGuestEmail())
                    .build();
            reservationDTOs.add(current);
        }
        return reservationDTOs;
    }

    private static AddressDTO mapToAddressDTO(Address address) {
        return AddressDTO.builder()
                .country(address.getCountry())
                .state(address.getState())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
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
