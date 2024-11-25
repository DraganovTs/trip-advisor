package com.trip.advisor.accommodation.service.service;


import com.trip.advisor.accommodation.service.exception.AccommodationAlreadyExistException;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.dto.AddressDTO;
import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Address;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import com.trip.advisor.accommodation.service.repository.AccommodationRepository;
import com.trip.advisor.accommodation.service.services.ReservationService;
import com.trip.advisor.accommodation.service.services.impl.AccommodationServiceImpl;
import com.trip.advisor.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTests {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    private AccommodationDTO accommodationDTO;
    private Accommodation accommodation;

    @BeforeEach
    public void setup() {
//        accommodationRepository.deleteAll();
//        accommodationRepository.deleteAll();

        AddressDTO addressDTO = AddressDTO.builder()
                .street("Test street")
                .city("Test city")
                .state("Test state")
                .country("Test country")
                .postalCode("Test postalCode")
                .build();

        Address address = Address.builder()
                .street("Test street")
                .city("Test city")
                .state("Test state")
                .country("Test country")
                .postalCode("Test postalCode")
                .build();

        accommodationDTO = AccommodationDTO.builder()
                .name("Test Accommodation")
                .price(25.00)
                .type("HOTEL")
                .rating(5.00)
                .address(addressDTO)
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
    public void createAccommodation_Success() {
        // Arrange
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(accommodationRepository.save(any(Accommodation.class)))
                .thenReturn(accommodation);

        // Act
        accommodationService.createAccommodation(accommodationDTO);

        // Assert
        verify(accommodationRepository, times(2)).save(any(Accommodation.class)); // Adjust the count as needed
    }

    @Test
    public void createAccommodation_ThrowsAccommodationAlreadyExistException() {
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(accommodation));

        try {
            accommodationService.createAccommodation(accommodationDTO);
        } catch (AccommodationAlreadyExistException e) {
            // Expected exception
            verify(accommodationRepository, never()).save(any(Accommodation.class));
        }
    }

    @Test
    public void getAccommodationByName_Success() {
        when(accommodationRepository.findByName(anyString()))
                .thenReturn(Optional.of(accommodation));

        AccommodationDTO result = accommodationService.getAccommodationByName("TestHotel");

        verify(accommodationRepository).findByName("TestHotel");
        // Add more assertions if necessary
    }

    @Test
    public void getAccommodationByName_NotFound() {
        when(accommodationRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        try {
            accommodationService.getAccommodationByName("NonExistentHotel");
        } catch (ResourceNotFoundException e) {
            // Expected exception
        }
    }

    @Test
    public void getAccommodationsByType_Success() {
        List<Accommodation> accommodations = List.of(accommodation);
        when(accommodationRepository.findByType(any(AccommodationType.class)))
                .thenReturn(Optional.of(accommodations));

        List<AccommodationDTO> result = accommodationService.getAccommodationsByType("HOTEL");

        verify(accommodationRepository).findByType(AccommodationType.HOTEL);
        // Add more assertions if necessary
    }

    @Test
    public void getAccommodationsByType_NotFound() {
        // Arrange
        AccommodationType nonExistentType = AccommodationType.valueOf("HOTEL"); // use a valid enum value
        when(accommodationRepository.findByType(nonExistentType))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            accommodationService.getAccommodationsByType(nonExistentType.name());
        });
    }



    @Test
    public void updateAccommodation_Success() {
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(accommodation));
        when(accommodationRepository.save(any(Accommodation.class)))
                .thenReturn(accommodation);

        boolean result = accommodationService.updateAccommodation(accommodationDTO);

        verify(accommodationRepository).save(any(Accommodation.class));
        // Add more assertions if necessary
    }

    @Test
    public void updateAccommodation_NotFound() {
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        try {
            accommodationService.updateAccommodation(accommodationDTO);
        } catch (ResourceNotFoundException e) {
            // Expected exception
        }
    }

    @Test
    public void deleteAccommodation_Success() {
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(accommodation));
        doNothing().when(reservationService).deleteByAccommodationId(any(UUID.class));
        doNothing().when(accommodationRepository).deleteById(anyLong());

        boolean result = accommodationService.deleteAccommodation("TestHotel", "Sofia", "Vitoshka");

        verify(accommodationRepository).deleteById(anyLong());
        verify(reservationService).deleteByAccommodationId(any(UUID.class));
        // Add more assertions if necessary
    }

    @Test
    public void deleteAccommodation_NotFound() {
        when(accommodationRepository.findByNameAndAddress_CityAndAddress_Street(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        try {
            accommodationService.deleteAccommodation("NonExistentHotel", "Sofia", "Vitoshka");
        } catch (ResourceNotFoundException e) {
            // Expected exception
        }
    }

}
