package com.trip.advisor.events.service.model.entity;

import com.trip.advisor.events.service.model.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Event extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type eventType;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private double ticketPrice;


}
