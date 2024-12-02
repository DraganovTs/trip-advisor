package com.trip.advisor.message.service.service.handler;

import com.trip.advisor.common.commands.ApproveReservationCommand;
import com.trip.advisor.common.events.FailedReservationMessageEvent;
import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.InformationCollectorService;
import com.trip.advisor.message.service.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topic.messageCommand}")
public class MessageEventHandler {

    private final InformationCollectorService informationCollectorService;
    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

    public MessageEventHandler(InformationCollectorService informationCollectorService, MessageService messageService) {
        this.informationCollectorService = informationCollectorService;
        this.messageService = messageService;
    }

    @KafkaHandler
    public void handleEvent(@Payload FailedReservationMessageEvent event) {
        logger.info("************* Handling FailedReservationMessageEvent: {}", event);
        EmailDTO emailDTO = informationCollectorService.fetchData(
                event.getAccommodationId(),
                event.getReservationId(),
                event.getUserId());

        messageService.sendEmail(emailDTO);
    }

    @KafkaHandler
    public void handleEvent(@Payload ApproveReservationCommand command) {
        logger.info("************* Handling ApproveReservationCommand: {}", command);
        EmailDTO emailDTO = informationCollectorService.fetchData(
                command.getAccommodationId(),
                command.getReservationId(),
                command.getUserId());

        messageService.sendEmail(emailDTO);
    }
}
