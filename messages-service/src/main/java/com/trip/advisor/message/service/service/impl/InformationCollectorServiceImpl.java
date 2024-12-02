package com.trip.advisor.message.service.service.impl;

import com.trip.advisor.message.service.service.InformationCollectorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InformationCollectorServiceImpl implements InformationCollectorService {

    private final RestTemplate restTemplate;
    private static final String ACCOMMODATION_URL = "http://localhost:8080/api/fetch/accommodationId";

    public InformationCollectorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void fetchData() {

    }
}
