package com.trip.advisor.accommodation.service.exception;


import com.trip.advisor.common.exception.GlobalExceptionHandler;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalAccommodationExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(AccommodationAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccommodationAlreadyExistExist(AccommodationAlreadyExistException ex,
                                                                                 WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }


}
