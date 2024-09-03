package com.trip.advisor.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class TripPlanDTO {
    List<AccommodationDTO> accommodationDTOList;
    List<EventDTO> eventDTOList;
    List<RecommendationDTO> recommendationDTOList;


}
