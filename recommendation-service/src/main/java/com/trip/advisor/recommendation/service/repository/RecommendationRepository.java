package com.trip.advisor.recommendation.service.repository;

import com.trip.advisor.recommendation.service.model.entity.Recommendation;
import com.trip.advisor.recommendation.service.model.enums.RecommendationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Optional<Recommendation> findByNameAndCity(String name, String city);

    Optional<List<Recommendation>> findAllByCity(String city);

    Optional<List<Recommendation>> findAllByCityAndType(String city, RecommendationType type);

}
