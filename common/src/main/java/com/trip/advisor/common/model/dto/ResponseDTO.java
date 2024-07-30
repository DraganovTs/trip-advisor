package com.trip.advisor.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "ResponseDTO",
        description = "Schema for standard API response containing status code and status message"
)
public class ResponseDTO {

    @Schema(
            description = "Status code of the response",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message of the response",
            example = "Success"
    )
    private String statusMessage;
}
