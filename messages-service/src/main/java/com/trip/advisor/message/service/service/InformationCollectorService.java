package com.trip.advisor.message.service.service;

import java.util.UUID;

public interface InformationCollectorService {

    public void fetchData(UUID accommodationId, UUID reservationId, UUID userId);
}
