package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccommodationRepositoryTests {

    @Autowired
    private AccommodationRepository accommodationRepository;

    private Address address;
    private Accommodation accommodation;


    @BeforeEach
    public void setUp() {
        accommodationRepository.deleteAll();

        address = Address.builder()
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
    }


    @Test
    public void AccommodationRepository_Save_ReturnSavedAccommodation() {
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        Assertions.assertNotNull(savedAccommodation);
        Assertions.assertEquals(accommodation.getName(), savedAccommodation.getName());
    }

    @Test
    public void AccommodationRepository_FindById_ReturnAccommodation() {
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        Accommodation foundAccommodation = accommodationRepository.findByAccommodationId(savedAccommodation.getAccommodationId()).orElse(null);

        Assertions.assertNotNull(foundAccommodation);
        Assertions.assertEquals(savedAccommodation.getAccommodationId(), foundAccommodation.getAccommodationId());
    }

    @Test
    public void AccommodationRepository_FindAll_ReturnAllAccommodations() {
        accommodationRepository.save(accommodation);

        Iterable<Accommodation> accommodations = accommodationRepository.findAll();

        Assertions.assertNotNull(accommodations);
        Assertions.assertTrue(accommodations.iterator().hasNext());
    }

    @Test
    public void AccommodationRepository_Update_ReturnUpdatedAccommodation() {
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        savedAccommodation.setPrice(30.00);
        Accommodation updatedAccommodation = accommodationRepository.save(savedAccommodation);

        Assertions.assertEquals(30.00, updatedAccommodation.getPrice());
        Assertions.assertEquals(savedAccommodation.getAccommodationId(), updatedAccommodation.getAccommodationId());
    }

    @Test
    public void AccommodationRepository_Delete_ReturnEmptyResult() {
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        accommodationRepository.deleteAccommodationByAccommodationId(savedAccommodation.getAccommodationId());
        Accommodation deletedAccommodation = accommodationRepository.findByAccommodationId(savedAccommodation.getAccommodationId())
                .orElse(null);

        Assertions.assertNull(deletedAccommodation);
    }



    @Test
    public void AccommodationRepository_FindByName_ReturnAccommodation() {
        accommodationRepository.save(accommodation);
        Optional<Accommodation> optionalAccommodation = accommodationRepository.findByName("TestHotel");

        Assertions.assertTrue(optionalAccommodation.isPresent());
        Accommodation foundAccommodation = optionalAccommodation.get();
        Assertions.assertEquals(accommodation.getName(), foundAccommodation.getName());
    }


    @Test
    public void AccommodationRepository_FindByNameAndCityAndStreet_ReturnAccommodation() {
        accommodationRepository.save(accommodation);
        Optional<Accommodation> optionalAccommodation = accommodationRepository.findByNameAndAddress_CityAndAddress_Street(
                "TestHotel", "Sofia", "Vitoshka");

        Assertions.assertTrue(optionalAccommodation.isPresent());
        Accommodation foundAccommodation = optionalAccommodation.get();
        Assertions.assertEquals(accommodation.getName(), foundAccommodation.getName());
        Assertions.assertEquals("Sofia", foundAccommodation.getAddress().getCity());
        Assertions.assertEquals("Vitoshka", foundAccommodation.getAddress().getStreet());
    }

    @Test
    public void AccommodationRepository_FindByType_ReturnListOfAccommodations() {
        accommodationRepository.save(accommodation);
        Optional<List<Accommodation>> optionalHotels = accommodationRepository.findByType(AccommodationType.HOTEL);

        Assertions.assertTrue(optionalHotels.isPresent());
        List<Accommodation> hotels = optionalHotels.get();
        Assertions.assertFalse(hotels.isEmpty());
        Assertions.assertEquals(1, hotels.size());
        Assertions.assertEquals(accommodation.getName(), hotels.get(0).getName());
    }


}
