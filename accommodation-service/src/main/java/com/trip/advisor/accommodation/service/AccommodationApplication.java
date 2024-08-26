package com.trip.advisor.accommodation.service;

import com.trip.advisor.accommodation.service.model.dto.AccommodationContactInfoDTO;
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
@EnableConfigurationProperties(value = AccommodationContactInfoDTO.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Accommodation microservice REST API Documentation",
                description = "Trip adviser Accommodation microservice REST API Documentation",
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
                description = "Accommodation microservice REST API Documentation",
                url = "https://tze.com"
        )
)
public class AccommodationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccommodationApplication.class, args);
    }
}
