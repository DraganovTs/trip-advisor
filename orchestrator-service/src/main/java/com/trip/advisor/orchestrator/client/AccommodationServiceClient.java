package com.trip.advisor.orchestrator.client;

import com.trip.advisor.orchestrator.dto.AccommodationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class AccommodationServiceClient {

    private final RestClient restClient;

    public List<AccommodationDTO> getAccommodationsByType(String type) {
        return this.restClient.get()
                .uri("{type}",type)
                .retrieve()
                .body(new ParameterizedTypeReference<List<AccommodationDTO>>() {
                });
    }
}
