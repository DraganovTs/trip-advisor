package com.trip.advisor.accommodation.service.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    @Size(min = 3,max = 20)
    @NotEmpty(message = "Street is required")
    private String street;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "City is required")
    private String city;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "State is required")
    private String state;
    @NotNull
    @Size(min = 2,max = 20)
    @NotEmpty(message = "Country is required")
    private String country;
    @Size(min = 2,max = 10)
    @NotEmpty(message = "Postal Code is required")
    private String postalCode;
}
