package com.trip.advisor.reservation.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaConfig {

    @Value("${topics.reservationEvent}")
    private String reservationTopicName;
    @Value("${topics.accommodationCommands}")
    private String reserveAccommodationCommandTopicName;


    private final static Integer TOPIC_REPLICATION_FACTOR = 3;
    private final static Integer TOPIC_PARTITIONS = 3;


    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    NewTopic createReservationTopic() {
        return TopicBuilder
                .name(reservationTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    NewTopic createReserveAccommodationCommandTopic() {
        return TopicBuilder
                .name(reserveAccommodationCommandTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

}
