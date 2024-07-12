package com.trip.advisor.accommodation.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccommodationAlreadyExistException extends RuntimeException{


    public AccommodationAlreadyExistException(String message) {
        super(message);
    }
}
