package com.trip.advisor.accommodation.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotEmpty(message = "guest name must be not empty")
    @Size(min = 3, max = 20)
    private String guestName;
    @NotEmpty(message = "email must be not empty")
    @Email(message = "email must be valid")
    private String guestEmail;
}
