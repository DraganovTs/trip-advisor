package com.trip.advisor.orchestrator.config;

import com.trip.advisor.orchestrator.client.AccommodationServiceClient;
import com.trip.advisor.orchestrator.client.EventServiceClient;
import com.trip.advisor.orchestrator.client.RecommendationServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ServiceClientConfig {

    private static final Logger log = LoggerFactory.getLogger(ServiceClientConfig.class);

    @Bean
    public AccommodationServiceClient accommodationServiceClient(@Value("${accommodation.service.url}") String baseUrl){
        return new AccommodationServiceClient(buildRestClient(baseUrl));
    }


    @Bean
    public EventServiceClient eventServiceClient(@Value("${events.service.url}") String baseUrl){
        return new EventServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public RecommendationServiceClient recommendationServiceClient(@Value("${recommendation.service.url}") String baseUrl){
        return new RecommendationServiceClient(buildRestClient(baseUrl));
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("base Url: {}", baseUrl);
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
