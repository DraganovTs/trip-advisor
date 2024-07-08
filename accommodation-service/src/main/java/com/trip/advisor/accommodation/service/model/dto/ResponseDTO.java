package com.trip.advisor.accommodation.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private String statusCode;
    private String statusMessage;
}
