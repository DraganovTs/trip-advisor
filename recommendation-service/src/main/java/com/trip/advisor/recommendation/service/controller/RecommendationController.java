package com.trip.advisor.recommendation.service.controller;

import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.recommendation.service.constants.RecommendationConstants;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/recommendation", produces = {MediaType.APPLICATION_JSON_VALUE})
public class RecommendationController {

    private final RecommendationServiceImpl recommendationService;

    public RecommendationController(RecommendationServiceImpl recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createRecommendation(RecommendationDTO recommendationDTO) {

        recommendationService.createRecommendation(recommendationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        RecommendationConstants.STATUS_201,
                        RecommendationConstants.MESSAGE_201
                ));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateRecommendation(RecommendationDTO recommendationDTO) {
        boolean success = recommendationService.updateRecommendation(recommendationDTO);

        if (success) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(
                            Constants.STATUS_200,
                            Constants.MESSAGE_200
                    ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500, Constants.MESSAGE_500));
        }
    }

    @GetMapping("/fetch/{city}")
    public ResponseEntity<List<RecommendationDTO>> fetchRecommendation(@PathVariable("city") String city) {
        List<RecommendationDTO> recommendationDTOList = recommendationService.getRecommendationByCity(city);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationDTOList);
    }

    @GetMapping("/fetch/cityAndType")
    public ResponseEntity<List<RecommendationDTO>> fetchRecommendation(@RequestParam String city,
                                                                       @RequestParam String type) {
        List<RecommendationDTO> recommendationDTOList = recommendationService.getRecommendationByCityAndType(city,type);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationDTOList);
    }

    @DeleteMapping("/delete")
   public ResponseEntity<ResponseDTO> deleteRecommendationByCityAndName(@PathVariable String city,
                                                                        @PathVariable String name){
        boolean isDeleted = recommendationService.deleteByCityAndName(city,name);

        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_500,Constants.MESSAGE_500));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500,Constants.MESSAGE_500));
        }
    }


}
