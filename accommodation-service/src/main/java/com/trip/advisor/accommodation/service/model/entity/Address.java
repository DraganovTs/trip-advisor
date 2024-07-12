package com.trip.advisor.accommodation.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Size(min = 3,max = 20)
    @NotEmpty(message = "Street is required")
    private String street;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "City is required")
    private String city;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "State is required")
    private String state;
    @Size(min = 2,max = 20)
    @NotEmpty(message = "Country is required")
    private String country;
    @Size(min = 2,max = 10)
    @NotEmpty(message = "Postal Code is required")
    private String postalCode;

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
