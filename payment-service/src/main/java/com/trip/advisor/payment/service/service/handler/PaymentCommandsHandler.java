package com.trip.advisor.payment.service.service.handler;

import com.trip.advisor.payment.service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.paymentsCommand}")
public class PaymentCommandsHandler {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentCommandsHandler.class);
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final String paymentsEventTopicName;

    public PaymentCommandsHandler(PaymentService paymentService,
                                  KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${topics.paymentsEvent}") String paymentsEventTopicName) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentsEventTopicName = paymentsEventTopicName;
    }
}
