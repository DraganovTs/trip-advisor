package com.trip.advisor.orchestrator.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Accommodation",
        description = "Schema to hold Accommodation information"
)
public class AccommodationDTO {
    @Schema(
            description = "Name of the accommodation",
            example = "Holiday Inn"
    )
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @Schema(
            description = "Price of the accommodation per night",
            example = "150.0"
    )
    @Positive(message = "Price must be a positive value")
    private double price;

    @Schema(
            description = "Type of accommodation (e.g., hotel, motel, hostel)",
            example = "Hotel"
    )
    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @Schema(
            description = "Rating of the accommodation between 0 and 10",
            example = "8.5"
    )
    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating must be at most 10.0")
    private double rating;

    @Schema(
            description = "Address details of the accommodation"
    )
    @NotNull(message = "Address cannot be null")
    private AddressDTO address;

    @Schema(
            description = "List of reservations for the accommodation"
    )
    private List<ReservationDTO> reservations;

}
