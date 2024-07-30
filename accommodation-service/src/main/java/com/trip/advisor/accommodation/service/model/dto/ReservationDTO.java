package com.trip.advisor.accommodation.service.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ReservationDTO",
        description = "Schema to hold reservation information"
)
public class ReservationDTO {

    @Schema(
            description = "Start date of the reservation",
            example = "2024-07-20"
    )
    private LocalDate startDate;

    @Schema(
            description = "End date of the reservation",
            example = "2024-07-25"
    )
    private LocalDate endDate;

    @Schema(
            description = "Name of the guest",
            example = "John Doe"
    )
    @NotEmpty(message = "Guest name must not be empty")
    @Size(min = 3, max = 20, message = "Guest name must be between 3 and 20 characters")
    private String guestName;

    @Schema(
            description = "Email address of the guest",
            example = "john.doe@example.com"
    )
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String guestEmail;
}
