package com.trip.advisor.orchestrator.service;

import com.trip.advisor.orchestrator.client.AccommodationServiceClient;
import com.trip.advisor.orchestrator.client.EventServiceClient;
import com.trip.advisor.orchestrator.client.RecommendationServiceClient;
import com.trip.advisor.orchestrator.dto.AccommodationDTO;
import com.trip.advisor.orchestrator.dto.TripPlanDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private static final Logger log = LoggerFactory.getLogger(TripPlanService.class);
    private final AccommodationServiceClient accommodationServiceClient;
    private final EventServiceClient eventServiceClient;
    private final RecommendationServiceClient recommendationServiceClient;
    private final ExecutorService executor;

    public TripPlanDTO getTripPlan(String type, String city) {
        var accommodations = this.executor.submit(() -> this.accommodationServiceClient.getAccommodationsByType(type));
//        var events = this.executor.submit(() -> this.eventServiceClient.getAllEventsInCity(city));
//        var recommendations = this.executor.submit(() -> this.recommendationServiceClient.getAllRecommendationsInCity(city));

        return new TripPlanDTO(
                getOrElse(accommodations, Collections.emptyList()),
                null,
                null
        );
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            log.error("error", e);
        }
        return defaultValue;
    }


}
