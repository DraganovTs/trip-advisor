package com.trip.advisor.reservation.service.controller;

import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.reservation.service.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.reservation.service.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity<CreateReservationResponseDTO> createReservation(
            @RequestBody @Valid CreateReservationRequestDTO createReservationRequestDTO) {
        CreateReservationResponseDTO response = reservationService.placeReservation(createReservationRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
