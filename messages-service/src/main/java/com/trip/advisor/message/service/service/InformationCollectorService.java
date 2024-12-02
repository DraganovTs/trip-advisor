package com.trip.advisor.message.service.service;

import com.trip.advisor.message.service.model.dto.EmailDTO;

import java.util.UUID;

public interface InformationCollectorService {

     EmailDTO fetchData(UUID accommodationId, UUID reservationId, UUID userId);
}
