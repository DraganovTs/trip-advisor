package com.trip.advisor.orchestrator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "RecommendationDTO",
        description = "Schema to hold recommendation information for restaurants, cafes, sightseeing, etc."
)
public class RecommendationDTO {

    @Schema(
            description = "Name of the recommendation",
            example = "Central Park"
    )
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Schema(
            description = "Type of the recommendation (e.g., restaurant, cafe, sightseeing)",
            example = "Sightseeing"
    )
    @NotNull(message = "Type is required")
    private String type;

    @Schema(
            description = "City where the recommendation is located",
            example = "New York"
    )
    @NotNull(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @Schema(
            description = "Address of the recommendation",
            example = "Central Park West, New York, NY 10024"
    )
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @Schema(
            description = "Description of the recommendation",
            example = "A large public park in New York City."
    )
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Schema(
            description = "Rating of the recommendation",
            example = "4.5"
    )
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5.0")
    private Double rating;

    @Schema(
            description = "Website URL of the recommendation",
            example = "https://www.centralparknyc.org"
    )
    @Pattern(regexp = "^https?://.*", message = "Website must be a valid URL")
    private String website;

    @Schema(
            description = "Contact number of the recommendation",
            example = "+1234567890"
    )
    @Pattern(regexp = "^[+]?[0-9]*$", message = "Contact number must be a valid phone number")
    private String contactNumber;

    @Schema(
            description = "Email address of the recommendation",
            example = "info@centralparknyc.org"
    )
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Opening hours of the recommendation",
            example = "9 AM - 5 PM"
    )
    @Size(max = 255, message = "Opening hours must be less than 255 characters")
    private String openingHours;
}
