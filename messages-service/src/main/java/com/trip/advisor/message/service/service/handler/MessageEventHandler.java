package com.trip.advisor.message.service.service.handler;

import com.trip.advisor.common.commands.ApproveReservationCommand;
import com.trip.advisor.common.events.FailedReservationMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topic.messageCommand}")
public class MessageEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

    @KafkaHandler
    public void handleEvent(@Payload FailedReservationMessageEvent event) {
        logger.info("************* Handling FailedReservationMessageEvent: {}", event);
    }

    @KafkaHandler
    public void handleEvent(@Payload ApproveReservationCommand command) {
        logger.info("************* Handling ApproveReservationCommand: {}", command);
    }
}
