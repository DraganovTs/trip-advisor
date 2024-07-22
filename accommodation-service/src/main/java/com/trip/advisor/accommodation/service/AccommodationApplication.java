package com.trip.advisor.accommodation.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AccommodationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccommodationApplication.class, args);
    }
}
