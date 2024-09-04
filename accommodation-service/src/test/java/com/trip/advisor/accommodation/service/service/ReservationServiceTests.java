package com.trip.advisor.accommodation.service.service;

import com.trip.advisor.accommodation.service.mapper.AccommodationMapper;
import com.trip.advisor.accommodation.service.model.dto.ReservationDTO;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.repository.ReservationRepository;
import com.trip.advisor.accommodation.service.services.impl.ReservationServiceImpl;
import com.trip.advisor.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void createReservation_Success() {
        try (var mockedStatic = Mockito.mockStatic(AccommodationMapper.class)) {
            // Arrange
            ReservationDTO reservationDTO = new ReservationDTO();
            Reservation reservation = new Reservation();

            mockedStatic.when(() -> AccommodationMapper.mapReservationDTOToReservation(reservationDTO))
                    .thenReturn(reservation);
            when(reservationRepository.save(reservation)).thenReturn(reservation);

            // Act
            Reservation result = reservationService.createReservation(reservationDTO);

            // Assert
            assertNotNull(result);
            verify(reservationRepository).save(reservation);
        }
    }

    @Test
    void getAllReservations_Success() {
        try (var mockedStatic = Mockito.mockStatic(AccommodationMapper.class)) {
            // Arrange
            Reservation reservation = new Reservation();
            ReservationDTO reservationDTO = new ReservationDTO();
            List<Reservation> reservations = List.of(reservation);
            List<ReservationDTO> reservationDTOs = List.of(reservationDTO);

            when(reservationRepository.findAll()).thenReturn(reservations);
            mockedStatic.when(() -> AccommodationMapper.mapReservationsToReservationDTOs(reservations))
                    .thenReturn(reservationDTOs);

            // Act
            List<ReservationDTO> result = reservationService.getAllReservations();

            // Assert
            assertEquals(reservationDTOs, result);
            verify(reservationRepository).findAll();
        }
    }

    @Test
    void getReservationById_Success() {
        try (var mockedStatic = Mockito.mockStatic(AccommodationMapper.class)) {
            // Arrange
            long reservationId = 1L;
            Reservation reservation = new Reservation();
            ReservationDTO reservationDTO = new ReservationDTO();

            when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
            mockedStatic.when(() -> AccommodationMapper.mapReservationToReservationDTO(reservation))
                    .thenReturn(reservationDTO);

            // Act
            ReservationDTO result = reservationService.getReservationById(reservationId);

            // Assert
            assertEquals(reservationDTO, result);
        }
    }

    @Test
    void getReservationById_NotFound() {
        // Arrange
        long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reservationService.getReservationById(reservationId));
    }

    @Test
    void updateReservation_Success() {
        try (var mockedStatic = Mockito.mockStatic(AccommodationMapper.class)) {
            // Arrange
            long reservationId = 1L;
            ReservationDTO reservationDTO = new ReservationDTO();
            Reservation existingReservation = new Reservation();
            Reservation updatedReservation = new Reservation();

            when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existingReservation));
            mockedStatic.when(() -> AccommodationMapper.mapReservationDTOToReservation(reservationDTO))
                    .thenReturn(updatedReservation);
            when(reservationRepository.save(updatedReservation)).thenReturn(updatedReservation);
            mockedStatic.when(() -> AccommodationMapper.mapReservationToReservationDTO(updatedReservation))
                    .thenReturn(reservationDTO);

            // Act
            ReservationDTO result = reservationService.updateReservation(reservationId, reservationDTO);

            // Assert
            assertEquals(reservationDTO, result);
            verify(reservationRepository).save(updatedReservation);
        }
    }

    @Test
    void updateReservation_NotFound() {
        // Arrange
        long reservationId = 1L;
        ReservationDTO reservationDTO = new ReservationDTO();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reservationService.updateReservation(reservationId, reservationDTO));
    }

    @Test
    void deleteReservation_Success() {
        // Arrange
        long reservationId = 1L;
        when(reservationRepository.existsById(reservationId)).thenReturn(true);

        // Act
        reservationService.deleteReservation(reservationId);

        // Assert
        verify(reservationRepository).deleteById(reservationId);
    }

    @Test
    void deleteReservation_NotFound() {
        // Arrange
        long reservationId = 1L;
        when(reservationRepository.existsById(reservationId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reservationService.deleteReservation(reservationId));
    }

    @Test
    void initializeReservation_Success() {
        try (var mockedStatic = Mockito.mockStatic(AccommodationMapper.class)) {
            // Arrange
            Long accommodationId = 1L;
            ReservationDTO reservationDTO = new ReservationDTO();
            Reservation reservation = new Reservation();
            List<ReservationDTO> reservationDTOs = List.of(reservationDTO);
            List<Reservation> reservations = List.of(reservation);

            mockedStatic.when(() -> AccommodationMapper.mapReservationDTOToReservation(reservationDTO))
                    .thenReturn(reservation);
            when(reservationRepository.saveAll(reservations)).thenReturn(reservations);

            // Act
            List<Reservation> result = reservationService.initializeReservation(reservationDTOs, accommodationId);

            // Assert
            assertEquals(reservations, result);
        }
    }

    @Test
    void deleteByAccommodationId_Success() {
        // Arrange
        long accommodationId = 1L;

        // Act
        reservationService.deleteByAccommodationId(accommodationId);

        // Assert
        verify(reservationRepository).deleteReservationByAccommodationId(accommodationId);
    }
}
