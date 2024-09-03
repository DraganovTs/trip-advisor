package com.trip.advisor.orchestrator.client;

import com.trip.advisor.orchestrator.dto.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class EventServiceClient {

    private final RestClient restClient;

    public List<EventDTO>getAllEventsInCity(String city){
        return this.restClient.get()
                .uri("{city}",city)
                .retrieve()
                .body(new ParameterizedTypeReference<List<EventDTO>>() {
                });
    }
}
