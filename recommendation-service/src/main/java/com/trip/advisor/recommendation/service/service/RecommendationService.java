package com.trip.advisor.recommendation.service.service;

import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;

import java.util.List;

public interface RecommendationService {

    /**
     * create recommendation from RecommendationDTO
     *
     * @param recommendationDTO
     */
    void createRecommendation(RecommendationDTO recommendationDTO);

    /**
     * update recommendation base on recommendationDTO
     *
     * @param recommendationDTO
     * @return
     */
    boolean updateRecommendation(RecommendationDTO recommendationDTO);

    /**
     * @param city
     * @return list of recommendations based of given city
     */
    List<RecommendationDTO> getRecommendationByCity(String city);

    /**
     * @param city
     * @param type
     * @return list of recommendations based of given city and type of recommendation
     */
    List<RecommendationDTO> getRecommendationByCityAndType(String city, String type);

    /**
     * @param city
     * @param name
     * @return boolean is deleted
     */
    Boolean deleteByCityAndName(String city, String name);

}
