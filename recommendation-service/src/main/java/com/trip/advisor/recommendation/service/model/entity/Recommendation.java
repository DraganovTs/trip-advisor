package com.trip.advisor.recommendation.service.model.entity;

import com.trip.advisor.recommendation.service.model.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Recommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationType type;

    @Column(nullable = false)
    private String city;

    private String address;
    private String description;
    private Double rating;
    private String website;
    private String contactNumber;
    private String email;
    private String openingHours;

}
