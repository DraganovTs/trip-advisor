package com.trip.advisor.accommodation.service.services.handler;

import com.trip.advisor.accommodation.service.services.AccommodationService;
import com.trip.advisor.accommodation.service.services.ReservationService;
import com.trip.advisor.common.commands.ReserveAccommodationCommand;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.accommodationCommands}")
public class AccommodationCommandsHandler {

    private final ReservationService reservationService;
    private final AccommodationService accommodationService;

    public AccommodationCommandsHandler(ReservationService reservationService, AccommodationService accommodationService) {
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;
    }

    @KafkaHandler
    private void handleCommand(@Payload ReserveAccommodationCommand command){
        boolean isReserved = reservationService.checkIfIsAlreadyReserved(
                command.getStartDate(),
                command.getEndDate(),
                command.getAccommodationId());
    }
}
