package com.trip.advisor.recommendation.service.controller;

import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.recommendation.service.constants.RecommendationConstants;
import com.trip.advisor.recommendation.service.model.dto.RecommendationContactInfoDTO;
import com.trip.advisor.recommendation.service.model.dto.RecommendationDTO;
import com.trip.advisor.recommendation.service.service.impl.RecommendationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "CRUD REST API for Recommendations in TripAdvisor",
        description = "CRUD REST APIs in TripAdvisor to CREATE, UPDATE, FETCH, and DELETE recommendation details"
)
@RestController
@RequestMapping(value = "/api/recommendation", produces = {MediaType.APPLICATION_JSON_VALUE})
public class RecommendationController {

    private final RecommendationServiceImpl recommendationService;
    @Value("${build.version}")
    private String buildVersion;
    private final Environment environment;
    private final RecommendationContactInfoDTO eventsContactInfoDTO;


    public RecommendationController(RecommendationServiceImpl recommendationService, Environment environment, RecommendationContactInfoDTO eventsContactInfoDTO) {
        this.recommendationService = recommendationService;
        this.environment = environment;
        this.eventsContactInfoDTO = eventsContactInfoDTO;
    }
    @Operation(
            summary = "Create recommendation REST API",
            description = "REST API to create a recommendation in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
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
    @Operation(
            summary = "Update recommendation REST API",
            description = "REST API to update a recommendation in TripAdvisor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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
    @Operation(
            summary = "Fetch recommendations by city REST API",
            description = "REST API to fetch recommendations by city in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch/{city}")
    public ResponseEntity<List<RecommendationDTO>> fetchRecommendation(@PathVariable("city") String city) {
        List<RecommendationDTO> recommendationDTOList = recommendationService.getRecommendationByCity(city);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationDTOList);
    }
    @Operation(
            summary = "Fetch recommendations by city and type REST API",
            description = "REST API to fetch recommendations by city and type in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch/cityAndType")
    public ResponseEntity<List<RecommendationDTO>> fetchRecommendation(@RequestParam String city,
                                                                       @RequestParam String type) {
        List<RecommendationDTO> recommendationDTOList = recommendationService.getRecommendationByCityAndType(city,type);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recommendationDTOList);
    }
    @Operation(
            summary = "Delete recommendation by city and name REST API",
            description = "REST API to delete a recommendation by city and name in TripAdvisor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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


    @GetMapping("/build-info")
    public ResponseEntity<String>getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )

    })
    @GetMapping("/contact-info")
    public ResponseEntity<RecommendationContactInfoDTO> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventsContactInfoDTO);
    }



}
