package com.trip.advisor.accommodation.service.controller;

import com.trip.advisor.accommodation.service.constants.AccommodationConstants;
import com.trip.advisor.accommodation.service.model.dto.AccommodationContactInfoDTO;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST API for Accommodations in TripAdvisor",
        description = "CRUD REST APIs in TripAdvisor to CREATE, UPDATE, FETCH, and DELETE accommodation details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccommodationController {

    private final AccommodationService accommodationService;
    @Value("${build.version}")
    private String buildVersion;
    private final Environment environment;
    private final AccommodationContactInfoDTO accommodationContactInfoDTO;

    public AccommodationController(AccommodationService accommodationService, Environment environment,
                                   AccommodationContactInfoDTO accommodationContactInfoDTO) {
        this.accommodationService = accommodationService;
        this.environment = environment;
        this.accommodationContactInfoDTO = accommodationContactInfoDTO;
    }

    @Operation(
            summary = "Create accommodation REST API",
            description = "REST API to create an accommodation in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccommodation(@Valid @RequestBody AccommodationDTO accommodationDTO) {

        accommodationService.createAccommodation(accommodationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        AccommodationConstants.STATUS_201,
                        AccommodationConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch accommodation REST API",
            description = "REST API to fetch accommodation details by name in TripAdvisor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<AccommodationDTO> fetchAccommodation(@RequestParam
                                                               @Size(min = 2, max = 20, message = "name must be between 2 and 20")
                                                               String name) {

        AccommodationDTO accommodationDTO = accommodationService.getAccommodationByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accommodationDTO);
    }

    @Operation(
            summary = "Update accommodation REST API",
            description = "REST API to update an accommodation in TripAdvisor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccommodation(@Valid @RequestBody AccommodationDTO accommodationDTO) {
        boolean isUpdated = accommodationService.updateAccommodation(accommodationDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500, Constants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete accommodation REST API",
            description = "REST API to delete an accommodation in TripAdvisor by name, city, and street"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccommodation(@RequestParam
                                                           @Size(min = 2, max = 20, message = "name must be between 2 and 20")
                                                           String name,
                                                           @RequestParam
                                                           @Size(min = 2, max = 20, message = "name must be between 2 and 20")
                                                           String city,
                                                           @RequestParam
                                                           @Size(min = 2, max = 20, message = "name must be between 2 and 20")
                                                           String street) {
        boolean isDeleted = accommodationService.deleteAccommodation(name, city, street);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(Constants.STATUS_200, Constants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(Constants.STATUS_500, Constants.MESSAGE_500));
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
    public ResponseEntity<AccommodationContactInfoDTO> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accommodationContactInfoDTO);
    }


}
