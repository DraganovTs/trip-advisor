package com.trip.advisor.accommodation.service.model.entity;

import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accommodations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "accommodation_id")
    private UUID accommodationId;
    @Column(nullable = false,length = 20)
    @Size(min = 2, max = 20)
    private String name;
    @Column(nullable = false)
    @Positive
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType type;
    @Column(nullable = false)
    @DecimalMin(value = "0")
    @DecimalMax(value = "10")
    private double rating;
    @Column(nullable = false)
    @Basic
    private boolean available;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "accommodationId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setAccommodationId(this.accommodationId);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setAccommodationId(null);
    }
}
