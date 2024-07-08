package com.trip.advisor.accommodation.service.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressDTO {
    @Size(min = 3,max = 20)
    private String street;
    @NotNull
    @Size(min = 2,max = 20)
    private String city;
    @Size(min = 2,max = 20)
    private String state;
    @NotNull
    @Size(min = 2,max = 20)
    private String country;
    @Size(min = 2,max = 10)
    private String postalCode;
}
