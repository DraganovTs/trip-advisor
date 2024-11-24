package com.trip.advisor.reservation.service.saga;

import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import com.trip.advisor.common.events.ReservationCreatedEvent;
import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
import com.trip.advisor.reservation.service.service.ReservationHistoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.reservationEvent}")
public class ReservationSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String accommodationCommandTopicName;
    private final ReservationHistoryService reservationHistoryService;

    public ReservationSaga(KafkaTemplate<String, Object> kafkaTemplate,
                           @Value("${topics.accommodationCommands}") String accommodationCommandTopicName, ReservationHistoryService reservationHistoryService) {
        this.kafkaTemplate = kafkaTemplate;
        this.accommodationCommandTopicName = accommodationCommandTopicName;
        this.reservationHistoryService = reservationHistoryService;
    }

    @KafkaHandler
    public void handleEvent(@Payload ReservationCreatedEvent event) {
        ReserveAccommodationCommand command = new ReserveAccommodationCommand(
                event.getAccommodationId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getReservationId()
        );

        kafkaTemplate.send(accommodationCommandTopicName, command);

        reservationHistoryService.add(event.getReservationId(), ReservationStatus.CREATED);
    }
}
