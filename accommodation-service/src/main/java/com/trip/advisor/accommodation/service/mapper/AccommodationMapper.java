package com.trip.advisor.accommodation.service.mapper;

import com.trip.advisor.common.model.dto.AccommodationDTO;
import com.trip.advisor.common.model.dto.AddressDTO;
import com.trip.advisor.common.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
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

    public static AccommodationDTO mapAccommodationToAccommodationDTO(Accommodation accommodation) {
        return AccommodationDTO.builder()
                .name(accommodation.getName())
                .price(accommodation.getPrice())
                .type(accommodation.getType().toString())
                .rating(accommodation.getRating())
                .address(mapToAddressDTO(accommodation.getAddress()))
                .reservations(mapReservationsToReservationDTOs(accommodation.getReservations())) // Use the list mapping method
                .build();
    }


    public static ReservationDTO mapReservationToReservationDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .guestName(reservation.getGuestName())
                .guestEmail(reservation.getGuestEmail())
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

    public static List<ReservationDTO> mapReservationsToReservationDTOs(List<Reservation> reservations) {
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(mapReservationToReservationDTO(reservation));
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
                .street(addressDTO.getStreet())
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .city(addressDTO.getCity())
                .postalCode(addressDTO.getPostalCode())
                .build();
    }


    public static Reservation mapReserveAccommodationCommandToReservation(ReserveAccommodationCommand command) {
        return Reservation.builder()
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .guestName(command.getUserName())
                .guestEmail(command.getUserEmail())
                .accommodationId(command.getAccommodationId())
                .build();
    }
}
