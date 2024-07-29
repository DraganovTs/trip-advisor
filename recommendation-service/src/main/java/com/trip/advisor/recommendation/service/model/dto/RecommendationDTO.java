package com.trip.advisor.recommendation.service.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationDTO {

    @NotNull(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Type is required")
    private String type;

    @NotNull(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5.0")
    private Double rating;

    @Pattern(regexp = "^https?://.*", message = "Website must be a valid URL")
    private String website;

    @Pattern(regexp = "^[+]?[0-9]*$", message = "Contact number must be a valid phone number")
    private String contactNumber;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 255, message = "Opening hours must be less than 255 characters")
    private String openingHours;
}
