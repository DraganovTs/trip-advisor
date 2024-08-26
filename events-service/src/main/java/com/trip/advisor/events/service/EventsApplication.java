package com.trip.advisor.events.service;

import com.trip.advisor.events.service.model.dto.EventsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(EventsContactInfoDTO.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Events microservice REST API Documentation",
                description = "Trip adviser Events microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "DraganovTS",
                        email = "trip@gmail.com",
                        url = ""
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = ""
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Events microservice REST API Documentation",
                url = "https://tze.com"
        )
)
public class EventsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);

    }
}
