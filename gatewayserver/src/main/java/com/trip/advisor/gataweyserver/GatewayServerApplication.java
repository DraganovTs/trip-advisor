package com.trip.advisor.gataweyserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator tripadvisorRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/tripadvisor/accommodation/**")
                        .filters(f -> f.rewritePath("/tripadvisor/accommodation/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accommodationCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOMMODATION"))
                .route(p -> p
                        .path("/tripadvisor/events/**")
                        .filters(f -> f.rewritePath("/tripadvisor/events/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())

                                .circuitBreaker(config -> config.setName("eventCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://EVENTS"))
                .route(p -> p
                        .path("/tripadvisor/recommendation/**")
                        .filters(f -> f.rewritePath("/tripadvisor/recommendation/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("recommendationCircuitBreaker")))
                        .uri("lb://RECOMMENDATION"))
                .build();
    }
}
