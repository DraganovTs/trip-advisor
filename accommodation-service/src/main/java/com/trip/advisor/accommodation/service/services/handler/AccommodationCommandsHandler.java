package com.trip.advisor.accommodation.service.services.handler;

import com.trip.advisor.accommodation.service.services.AccommodationService;
import com.trip.advisor.accommodation.service.services.ReservationService;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import com.trip.advisor.common.events.AccommodationReservedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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
    private void handleCommand(@Payload ReserveAccommodationCommand command) throws ReflectiveOperationException {
        try {
            boolean isReserved = reservationService.checkIfIsAlreadyReserved(
                    command.getStartDate(),
                    command.getEndDate(),
                    command.getAccommodationId());
            //TODO create a reservation assign to accommodation
            AccommodationReservedEvent accommodationReservedEvent = new AccommodationReservedEvent(
                    command.getAccommodationId(),
                    100
            );
            kafkaTemplate.send(accommodationEventsTopicName, accommodationReservedEvent);
            //TODO get correct price maybe and name
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
        }


    }
}
