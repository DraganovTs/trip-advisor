package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {
}
