package com.trip.advisor.payment.service.service.handler;

import com.trip.advisor.common.commands.ProcessPaymentCommand;
import com.trip.advisor.common.events.PaymentFailedEvent;
import com.trip.advisor.common.events.PaymentProcessedEvent;
import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.payment.service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "payments-command")
public class PaymentCommandsHandler {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentCommandsHandler.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentsEventTopicName;

    public PaymentCommandsHandler(PaymentService paymentService,
                                  KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("payments-events") String paymentsEventTopicName) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentsEventTopicName = paymentsEventTopicName;
    }

    @KafkaHandler
    public void handleCommand(@Payload ProcessPaymentCommand command) {

        logger.info("******************** Handling command: ");
        try {
            logger.info("******************** Handling command: ProcessPaymentCommand ");
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .accommodationId(command.getAccommodationId())
                    .reservationId(command.getReservationId())
                    .price(command.getReservationPrice())
                    .accommodationName(command.getAccommodationName())
                    .build();

            PaymentDTO processedPayment = paymentService.process(paymentDTO);

            PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                    processedPayment.getReservationId(),
                    processedPayment.getId(),
                    command.getAccommodationName()
            );
            logger.info("******************** sending command paymentProcessedEvent");
            kafkaTemplate.send("payments-events", paymentProcessedEvent);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            logger.info(" ******************** sending command paymentFailedEvent");
            PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent(
                    command.getReservationId(),
                    command.getAccommodationId(),
                    command.getUserId(),
                    command.getAccommodationName(),
                    command.getStartDate(),
                    command.getEndDate()
            );

            kafkaTemplate.send("payments-events", paymentFailedEvent);
        }
    }
}
