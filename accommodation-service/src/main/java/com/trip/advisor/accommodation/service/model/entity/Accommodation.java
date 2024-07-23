package com.trip.advisor.accommodation.service.model.entity;

import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accommodations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accommodationId;
    @Column
    @Size(min = 2, max = 20)
    private String name;
    @Positive
    private double price;
    @Enumerated(EnumType.STRING)
    private AccommodationType type;
    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    private double rating;
    @Basic
    private boolean available;
    @Embedded
    private Address address;
    @OneToMany
    private List<Reservation> reservations;

}
