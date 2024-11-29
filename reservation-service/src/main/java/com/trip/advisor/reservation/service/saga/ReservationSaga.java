package com.trip.advisor.reservation.service.saga;

import com.trip.advisor.common.commands.*;
import com.trip.advisor.common.events.*;
import com.trip.advisor.reservation.service.model.enums.ReservationStatus;
import com.trip.advisor.reservation.service.service.ReservationHistoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@KafkaListener(topics = {"${topics.reservationEvent}",
        "${topics.accommodationEvents}"})
public class ReservationSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ReservationHistoryService reservationHistoryService;
    private final String accommodationCommandTopicName;
    private final String paymentsCommandTopicName;
    private final String messagesCommandTopicName;

    public ReservationSaga(KafkaTemplate<String, Object> kafkaTemplate,
                           ReservationHistoryService reservationHistoryService,
                           @Value("${topics.accommodationCommands}") String accommodationCommandTopicName,
                           @Value("${topics.paymentCommands}") String paymentsCommandTopicName,
                           @Value("${topics.messageCommand}") String messagesCommandTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.accommodationCommandTopicName = accommodationCommandTopicName;
        this.reservationHistoryService = reservationHistoryService;
        this.paymentsCommandTopicName = paymentsCommandTopicName;
        this.messagesCommandTopicName = messagesCommandTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload ReservationCreatedEvent event) {
        ReserveAccommodationCommand command = new ReserveAccommodationCommand(
                event.getAccommodationId(),
                event.getUserId(),
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
                event.getUserId(),
                event.getPrice(),
                event.getAccommodationName(),
                event.getStartDate(),
                event.getEndDate()
        );

        kafkaTemplate.send(paymentsCommandTopicName, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentProcessedEvent event) {
        ApproveReservationCommand approveReservationCommand = new ApproveReservationCommand(
                event.getReservationId(),
                event.getAccommodationName());
        reservationHistoryService.add(event.getReservationId(), ReservationStatus.APPROVED);
        kafkaTemplate.send(messagesCommandTopicName, approveReservationCommand);
    }

    @KafkaHandler
    public void handleEvent(@Payload AccommodationReservationFailedEvent event) {

        reservationHistoryService.add(event.getReservationId(), ReservationStatus.REJECTED);
        sendFailMessageToUser(event.getAccommodationId(), event.getReservationId(), event.getUserId());
    }


    @KafkaHandler
    public void handleEvent(@Payload PaymentFailedEvent event) {
        CancelAccommodationReservationCommand cancelReservationCommand = new CancelAccommodationReservationCommand(
                event.getAccommodationId(),
                event.getReservationId(),
                event.getStartDate(),
                event.getEndDate()
        );
        kafkaTemplate.send(accommodationCommandTopicName, cancelReservationCommand);
        sendFailMessageToUser(event.getAccommodationId(), event.getReservationId(), event.getUserId());
    }

    @KafkaHandler
    public void handleEvent(@Payload CancelAccommodationReservationCommand command) {
        reservationHistoryService.add(command.getReservationId(), ReservationStatus.REJECTED);
    }


    private void sendFailMessageToUser(UUID accommodationId, UUID reservationId, UUID userId) {

        FailedReservationMessageEvent failedReservationMessageEvent =
                new FailedReservationMessageEvent(
                        accommodationId,
                        reservationId,
                        userId
                );

        kafkaTemplate.send(messagesCommandTopicName, failedReservationMessageEvent);
    }


}
