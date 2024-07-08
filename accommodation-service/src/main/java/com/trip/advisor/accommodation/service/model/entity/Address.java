package com.trip.advisor.accommodation.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
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
