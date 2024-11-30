package com.trip.advisor.reservation.service.controller;

import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.reservation.service.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.reservation.service.model.entity.ReservationHistory;
import com.trip.advisor.reservation.service.service.ReservationHistoryService;
import com.trip.advisor.reservation.service.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationHistoryService reservationHistoryService;

    public ReservationController(ReservationService reservationService, ReservationHistoryService reservationHistoryService) {
        this.reservationService = reservationService;
        this.reservationHistoryService = reservationHistoryService;
    }

    @PostMapping("")
    public ResponseEntity<CreateReservationResponseDTO> createReservation(
            @RequestBody @Valid CreateReservationRequestDTO createReservationRequestDTO) {
        CreateReservationResponseDTO response = reservationService.placeReservation(createReservationRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationHistory>> getReservations() {
        return ResponseEntity.ok(reservationHistoryService.getReservationHistory());
    }



}
