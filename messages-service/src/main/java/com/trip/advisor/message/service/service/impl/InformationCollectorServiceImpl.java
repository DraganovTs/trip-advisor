package com.trip.advisor.message.service.service.impl;

import com.trip.advisor.common.model.dto.AccommodationDTO;
import com.trip.advisor.common.model.dto.CreateReservationResponseDTO;
import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.InformationCollectorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class InformationCollectorServiceImpl implements InformationCollectorService {

    private final RestTemplate restTemplate;
    private static final String ACCOMMODATION_SERVICE_URL = "http://localhost:8080/api/fetch/accommodationId";
    private static final String RESERVATION_SERVICE_URL = "http://localhost:8082//api/reservations/reservationId";
    private static final String USER_SERVICE_URL = "http://localhost:8080/api/fetch/accommodationId";


    public InformationCollectorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public EmailDTO fetchData(UUID accommodationId, UUID reservationId, UUID userId) {

        CompletableFuture<AccommodationDTO> accommodationFuture = getAccommodationById(accommodationId);
        CompletableFuture<CreateReservationResponseDTO> reservationFuture = getReservationById(reservationId);
        //TODO user service and call for user info

        CompletableFuture.allOf(accommodationFuture, reservationFuture).join();

        AccommodationDTO accommodationDTO = accommodationFuture.join();
        System.out.println("Fetched Accommodation: " + accommodationDTO);
        CreateReservationResponseDTO reservationResponseDTO = reservationFuture.join();
        System.out.println("Fetched Reservation: " + reservationResponseDTO);

        //TODO create mapper
        return new EmailDTO();
    }


    public CompletableFuture<AccommodationDTO> getAccommodationById(UUID accommodationId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = UriComponentsBuilder.fromHttpUrl(ACCOMMODATION_SERVICE_URL)
                        .queryParam("accommodationId", accommodationId)
                        .toUriString();

                return restTemplate.getForObject(url, AccommodationDTO.class);
            } catch (RestClientException e) {
                throw new RuntimeException("Failed to fetch accommodation data for ID: " + accommodationId, e);
            }
        });
    }

    private CompletableFuture<CreateReservationResponseDTO> getReservationById(UUID reservationId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = UriComponentsBuilder.fromHttpUrl(RESERVATION_SERVICE_URL)
                        .queryParam("reservationId", reservationId)
                        .toUriString();

                return restTemplate.getForObject(url, CreateReservationResponseDTO.class);
            } catch (RestClientException e) {
                throw new RuntimeException("Failed to fetch reservation data for ID: " + reservationId, e);
            }
        });
    }

}
