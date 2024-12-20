package com.trip.advisor.reservation.service.service.impl;

import com.trip.advisor.common.events.ReservationCreatedEvent;
import com.trip.advisor.common.model.dto.ReservationStatus;
import com.trip.advisor.reservation.service.mapper.ReservationMapper;
import com.trip.advisor.reservation.service.model.dto.CreateReservationRequestDTO;
import com.trip.advisor.common.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.reservation.service.model.entity.Reservation;
import com.trip.advisor.reservation.service.repository.ReservationRepository;
import com.trip.advisor.reservation.service.service.ReservationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String reservationEventTopicName;

    public ReservationServiceImpl(ReservationMapper reservationMapper,
                                  ReservationRepository reservationRepository,
                                  KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${topics.reservationEvent}") String reservationEventTopicName) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.reservationEventTopicName = reservationEventTopicName;
    }

    @Override
    public CreateReservationResponseDTO placeReservation(CreateReservationRequestDTO reservationRequestDTO) {
        var reservation = reservationMapper.mapReservationRequestToReservation(reservationRequestDTO);
        Reservation savedReservation = reservationRepository.save(reservation);

        ReservationCreatedEvent placeReservation = new ReservationCreatedEvent(
                savedReservation.getReservationId(),
                savedReservation.getUserId(),
                savedReservation.getAccommodationId(),
                savedReservation.getStartDate(),
                savedReservation.getEndDate(),
                savedReservation.getGuestName(),
                savedReservation.getGuestEmail()
        );

        kafkaTemplate.send(reservationEventTopicName, placeReservation);

        return reservationMapper.mapReservationToReservationResponse(savedReservation);
    }

    @Override
    public void approveReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Assert.notNull(reservation, "No reservation is found whit id " + reservationId + " in the database table");
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        reservationRepository.save(reservation);
    }

    @Override
    public void rejectReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Assert.notNull(reservation, "No reservation is found whit id " + reservationId + " in the database table");
        reservation.setReservationStatus(ReservationStatus.REJECTED);
        reservationRepository.save(reservation);
    }

    @Override
    public CreateReservationResponseDTO getReservationById(UUID reservationId) {
        return reservationMapper.mapReservationToReservationResponse(reservationRepository.findById(reservationId).get());
    }
}
