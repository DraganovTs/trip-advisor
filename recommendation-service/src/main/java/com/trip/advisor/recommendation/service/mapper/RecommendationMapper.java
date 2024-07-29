package com.trip.advisor.recommendation.service.mapper;

import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.model.enums.RecommendationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecommendationMapper {


    public static Recommendation mapRecommendationDTOToRecommendation(RecommendationDTO recommendationDTO) {
        return Recommendation.builder()
                .name(recommendationDTO.getName())
                .type(RecommendationType.valueOf(recommendationDTO.getType()))
                .city(recommendationDTO.getCity())
                .address(recommendationDTO.getAddress())
                .description(recommendationDTO.getDescription())
                .rating(recommendationDTO.getRating())
                .website(recommendationDTO.getWebsite())
                .contactNumber(recommendationDTO.getContactNumber())
                .email(recommendationDTO.getEmail())
                .openingHours(recommendationDTO.getOpeningHours())
                .build();
    }

    public static List<RecommendationDTO> mapRecommendationToRecommendationDTO(List<Recommendation> recommendationsInCity) {
        return recommendationsInCity.stream().map(recommendation ->
                RecommendationDTO.builder()
                        .name(recommendation.getName())
                        .type(recommendation.getType().toString())
                        .city(recommendation.getCity())
                        .address(recommendation.getAddress())
                        .description(recommendation.getDescription())
                        .rating(recommendation.getRating())
                        .website(recommendation.getWebsite())
                        .contactNumber(recommendation.getContactNumber())
                        .email(recommendation.getEmail())
                        .openingHours(recommendation.getOpeningHours())
                        .build()
        ).collect(Collectors.toList());
    }
}
