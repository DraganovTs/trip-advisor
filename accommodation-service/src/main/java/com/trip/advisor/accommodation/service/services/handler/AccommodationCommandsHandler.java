package com.trip.advisor.accommodation.service.services.handler;

import com.trip.advisor.accommodation.service.exception.ReservationOverlapping;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Transactional
    public void handleCommand(@Payload ReserveAccommodationCommand command)  {
        try {
            reservationService.checkIfIsAlreadyReserved(
                    command.getStartDate(),
                    command.getEndDate(),
                    command.getAccommodationId());

            Reservation savedReservation = reservationService.createReservationFromCommand(command);

            Accommodation accommodation = accommodationService.getAccommodationById(command.getAccommodationId());
            accommodation.addReservation(savedReservation);

            BigDecimal price = calculatePrice(accommodation.getPrice(), command.getStartDate(), command.getEndDate());

            publishAccommodationReservedEvent(command, price);

        } catch (ReservationOverlapping e) {
            handleReservationFailure(command, "Accommodation already reserved for the specified dates.");
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            handleReservationFailure(command, e.getMessage());
        }

    }

    private BigDecimal calculatePrice(double dailyRate, LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return BigDecimal.valueOf(dailyRate * days);
    }

    private void publishAccommodationReservedEvent(ReserveAccommodationCommand command, BigDecimal price) {
        AccommodationReservedEvent event = new AccommodationReservedEvent(
                command.getReservationId(),
                command.getAccommodationId(),
                price,
                command.getAccommodationName()
        );
        kafkaTemplate.send(accommodationEventsTopicName, event);
    }

    private void handleReservationFailure(ReserveAccommodationCommand command, String reason) {
        logger.warn("Reservation failed for accommodation {}: {}", command.getAccommodationId(), reason);
        AccommodationReservationFailedEvent failedEvent = new AccommodationReservationFailedEvent(
                command.getReservationId(),
                command.getAccommodationId(),
                command.getStartDate(),
                command.getEndDate(),
                command.getAccommodationName()
        );
        kafkaTemplate.send(accommodationEventsTopicName, failedEvent);
    }
}
