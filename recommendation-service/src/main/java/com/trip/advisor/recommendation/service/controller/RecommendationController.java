package com.trip.advisor.recommendation.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {


    @PostMapping("/create")
    public ResponseEntity<?> createRecommendation(){
        return ResponseEntity.ok().build();
    }

}
