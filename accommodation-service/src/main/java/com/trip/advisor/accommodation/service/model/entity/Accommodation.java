package com.trip.advisor.accommodation.service.model.entity;

import com.trip.advisor.accommodation.service.model.entity.enums.AccommodationType;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accommodationId;
    @Column
    @Size(min = 2,max = 20)
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "accommodation",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations;

}
