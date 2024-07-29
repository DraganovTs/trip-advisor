package com.trip.advisor.recommendation.service.exception;

public class RecommendationAlreadyExist extends RuntimeException{


    public RecommendationAlreadyExist(String message) {
        super(message);
    }
}
