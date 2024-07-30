package com.trip.advisor.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponseDTO",
        description = "Schema for detailed error responses, including path, error code, message, and timestamp"
)
public class ErrorResponseDTO {

    @Schema(
            description = "The API path where the error occurred",
            example = "/api/events/create"
    )
    private String apiPath;

    @Schema(
            description = "HTTP status code indicating the type of error",
            example = "400"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Detailed error message explaining what went wrong",
            example = "Invalid input provided"
    )
    private String errorMessage;

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2024-07-30T14:30:00Z"
    )
    private LocalDateTime errorTime;
}
