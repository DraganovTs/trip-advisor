package com.trip.advisor.reservation.service.saga;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${reservation.event.topic.name}")
public class ReservationSaga {

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public ReservationSaga(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
