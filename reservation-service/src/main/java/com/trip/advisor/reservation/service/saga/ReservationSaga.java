package com.trip.advisor.reservation.service.saga;

import com.trip.advisor.common.commands.ProcessPaymentCommand;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import com.trip.advisor.common.events.AccommodationReservedEvent;
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
@KafkaListener(topics = {"${topics.reservationEvent}",
        "${topics.accommodationEvents}"})
public class ReservationSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ReservationHistoryService reservationHistoryService;
    private final String accommodationCommandTopicName;
    private final String paymentsCommandTopicName;

    public ReservationSaga(KafkaTemplate<String, Object> kafkaTemplate,
                           ReservationHistoryService reservationHistoryService,
                           @Value("${topics.accommodationCommands}") String accommodationCommandTopicName,
                           @Value("${topics.paymentCommands}") String paymentsCommandTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.accommodationCommandTopicName = accommodationCommandTopicName;
        this.reservationHistoryService = reservationHistoryService;
        this.paymentsCommandTopicName = paymentsCommandTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload ReservationCreatedEvent event) {
        ReserveAccommodationCommand command = new ReserveAccommodationCommand(
                event.getAccommodationId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getReservationId(),
                event.getUserName(),
                event.getUserEmail()
        );

        kafkaTemplate.send(accommodationCommandTopicName, command);

        reservationHistoryService.add(event.getReservationId(), ReservationStatus.CREATED);
    }

    @KafkaHandler
    public void handleEvent(@Payload AccommodationReservedEvent event) {
        ProcessPaymentCommand command = new ProcessPaymentCommand(
                event.getReservationId(),
                event.getAccommodationId(),
                event.getPrice()
        );

        kafkaTemplate.send(paymentsCommandTopicName, command);
    }


}
