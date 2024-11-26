package com.trip.advisor.accommodation.service.services.handler;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.entity.Reservation;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import com.trip.advisor.accommodation.service.services.ReservationService;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import com.trip.advisor.common.events.AccommodationReservationFailedEvent;
import com.trip.advisor.common.events.AccommodationReservedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
@KafkaListener(topics = "${topics.accommodationCommands}")
public class AccommodationCommandsHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccommodationCommandsHandler.class);
    private final ReservationService reservationService;
    private final AccommodationService accommodationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String accommodationEventsTopicName;

    public AccommodationCommandsHandler(ReservationService reservationService, AccommodationService accommodationService,
                                        KafkaTemplate<String, Object> kafkaTemplate,
                                        @Value("${topics.accommodationEvents}") String accommodationEventsTopicName) {
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;
        this.kafkaTemplate = kafkaTemplate;
        this.accommodationEventsTopicName = accommodationEventsTopicName;
    }

    @KafkaHandler
    private void handleCommand(@Payload ReserveAccommodationCommand command)  {
        try {
            reservationService.checkIfIsAlreadyReserved(
                    command.getStartDate(),
                    command.getEndDate(),
                    command.getAccommodationId());
            Reservation savedReservation = reservationService.createReservationFromCommand(command);
            Accommodation accommodation = accommodationService.getAccommodationById(command.getAccommodationId());
            accommodation.addReservation(savedReservation);
            BigDecimal price = BigDecimal.valueOf(accommodation.getPrice() * ChronoUnit.DAYS.between(command.getStartDate(), command.getEndDate()));
            AccommodationReservedEvent accommodationReservedEvent = new AccommodationReservedEvent(
                    command.getReservationId(),
                    command.getAccommodationId(),
                    price
            );
            kafkaTemplate.send(accommodationEventsTopicName, accommodationReservedEvent);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            AccommodationReservationFailedEvent accommodationReservationFailedEvent = new AccommodationReservationFailedEvent(
                    command.getReservationId(),
                    command.getAccommodationId(),
                    command.getStartDate(),
                    command.getEndDate()
            );
            kafkaTemplate.send(accommodationEventsTopicName, accommodationReservationFailedEvent);
        }


    }
}
