package com.trip.advisor.accommodation.service.model.dto;


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
public class AccommodationDTO {

    @Size(min = 2,max = 20)
    private String name;
    @Positive
    private double price;
    private String type;
    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    private double rating;
    @NotNull
    private AddressDTO address;

    private List<ReservationDTO> reservations;

}
