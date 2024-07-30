package com.trip.advisor.accommodation.service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "AddressDTO",
        description = "Schema to hold address information"
)
public class AddressDTO {
    @Schema(
            description = "Street name and number",
            example = "123 Main St"
    )
    @Size(min = 3, max = 20, message = "Street must be between 3 and 20 characters")
    @NotEmpty(message = "Street is required")
    private String street;

    @Schema(
            description = "City name",
            example = "Springfield"
    )
    @Size(min = 2, max = 20, message = "City must be between 2 and 20 characters")
    @NotEmpty(message = "City is required")
    private String city;

    @Schema(
            description = "State or province name",
            example = "Illinois"
    )
    @Size(min = 2, max = 20, message = "State must be between 2 and 20 characters")
    @NotEmpty(message = "State is required")
    private String state;

    @Schema(
            description = "Country name",
            example = "USA"
    )
    @Size(min = 2, max = 20, message = "Country must be between 2 and 20 characters")
    @NotEmpty(message = "Country is required")
    private String country;

    @Schema(
            description = "Postal code",
            example = "62704"
    )
    @Size(min = 2, max = 10, message = "Postal Code must be between 2 and 10 characters")
    @NotEmpty(message = "Postal Code is required")
    private String postalCode;
}
