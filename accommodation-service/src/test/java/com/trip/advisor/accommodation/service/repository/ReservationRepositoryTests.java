package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class ReservationRepositoryTests {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    private Accommodation accommodation;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        accommodationRepository.deleteAll();

        Address address = Address.builder()
                .street("Vitoshka")
                .country("Bulgaria")
                .state("Sofia")
                .city("Sofia")
                .postalCode("1000")
                .build();

        accommodation = Accommodation.builder()
                .name("TestHotel")
                .price(25.00)
                .type(AccommodationType.HOTEL)
                .rating(5.00)
                .available(true)
                .address(address)
                .reservations(new ArrayList<>())
                .build();
        accommodationRepository.save(accommodation);

        reservation = Reservation.builder()
                .accommodationId(accommodation.getAccommodationId())  // Associate with the saved accommodation
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .guestName("Test guest")
                .guestEmail("Test@gmail.com")
                .build();
        reservationRepository.save(reservation);
    }

    @Test
    public void ReservationRepository_Save_ReturnSavedReservation() {

        Reservation savedReservation = reservationRepository.findById(reservation.getId()).orElse(null);

        Assertions.assertNotNull(savedReservation);
        Assertions.assertEquals("Test guest", savedReservation.getGuestName());
        Assertions.assertEquals(accommodation.getAccommodationId(), savedReservation.getAccommodationId());
    }

    @Test
    public void ReservationRepository_DeleteByAccommodationId_DeletesReservations() {

        reservationRepository.deleteReservationByAccommodationId(accommodation.getAccommodationId());

        List<Reservation> reservations = reservationRepository.findAll();
        Assertions.assertTrue(reservations.isEmpty());
    }

    @Test
    public void ReservationRepository_FindAll_ReturnsAllReservations() {

        List<Reservation> reservations = reservationRepository.findAll();

        Assertions.assertFalse(reservations.isEmpty());
        Assertions.assertEquals(1, reservations.size());
        Assertions.assertEquals("Test guest", reservations.get(0).getGuestName());
    }

    @Test
    public void ReservationRepository_DeleteByAccommodationId_NoEffectIfNoneExists() {

        reservationRepository.deleteReservationByAccommodationId(UUID.randomUUID());

        List<Reservation> reservations = reservationRepository.findAll();
        Assertions.assertFalse(reservations.isEmpty());
        Assertions.assertEquals(1, reservations.size());
    }
}
