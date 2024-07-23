package com.trip.advisor.accommodation.service.controller;

import com.trip.advisor.accommodation.service.constants.AccommodationConstants;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.common.constants.Constants;
import com.trip.advisor.common.model.dto.ResponseDTO;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accommodation", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccommodation(@Valid @RequestBody AccommodationDTO accommodationDTO) {

        accommodationService.createAccommodation(accommodationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        AccommodationConstants.STATUS_201,
                        AccommodationConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<AccommodationDTO> fetchAccommodation(@RequestParam
                                                               @Size(min = 2, max = 20,message = "name must be between 2 and 20")
                                                               String name) {

        AccommodationDTO accommodationDTO = accommodationService.getAccommodationByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accommodationDTO);
    }

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

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccommodation(@RequestParam
                                                           @Size(min = 2, max = 20 , message = "name must be between 2 and 20")
                                                           String name,
                                                           @RequestParam
                                                           @Size(min = 2, max = 20,message = "name must be between 2 and 20")
                                                           String city,
                                                           @RequestParam
                                                           @Size(min = 2, max = 20,message = "name must be between 2 and 20")
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


}
