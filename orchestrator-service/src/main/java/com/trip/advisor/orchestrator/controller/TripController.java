package com.trip.advisor.orchestrator.controller;

import com.trip.advisor.orchestrator.dto.TripPlanDTO;
import com.trip.advisor.orchestrator.service.TripPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TripController {

    private final TripPlanService tripPlanService;

    public TripController(TripPlanService tripPlanService) {
        this.tripPlanService = tripPlanService;
    }

    @GetMapping("/planTrip")
    public TripPlanDTO planTrip(@RequestParam String type,
                                @RequestParam String city) {
        return this.tripPlanService.getTripPlan(type, city);
    }

    @GetMapping("Test")
    public String test() {
        return "OK";
    }
}
