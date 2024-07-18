package com.trip.advisor.accommodation.service.controller;

import com.trip.advisor.accommodation.service.constants.AccommodationConstants;
import com.trip.advisor.accommodation.service.model.dto.AccommodationDTO;
import com.trip.advisor.accommodation.service.model.dto.ResponseDTO;
import com.trip.advisor.accommodation.service.services.AccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accommodation", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccommodation(@RequestBody AccommodationDTO accommodationDTO) {

        accommodationService.createAccommodation(accommodationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        AccommodationConstants.STATUS_201,
                        AccommodationConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<AccommodationDTO> fetchAccommodation(@RequestParam String name) {

        AccommodationDTO accommodationDTO = accommodationService.getAccommodationByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accommodationDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        boolean isUpdated = accommodationService.updateAccommodation(accommodationDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccommodationConstants.STATUS_200, AccommodationConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccommodationConstants.STATUS_500, AccommodationConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccommodation(@RequestParam String name,
                                                           @RequestParam String city,
                                                           @RequestParam String street) {
        boolean isDeleted = accommodationService.deleteAccommodation(name, city, street);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccommodationConstants.STATUS_200, AccommodationConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccommodationConstants.STATUS_500, AccommodationConstants.MESSAGE_500));
        }
    }


}
