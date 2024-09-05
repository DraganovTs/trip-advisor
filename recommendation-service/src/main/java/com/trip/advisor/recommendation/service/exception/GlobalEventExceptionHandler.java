package com.trip.advisor.recommendation.service.exception;

import com.trip.advisor.common.exception.GlobalExceptionHandler;
import com.trip.advisor.common.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalEventExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(RecommendationAlreadyExist.class)
    public ResponseEntity<ErrorResponseDTO> handleAccommodationAlreadyExistExist(RecommendationAlreadyExist ex,
                                                                                 WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

}
