package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import com.trip.advisor.accommodation.service.model.enums.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {

    Optional<Accommodation> findByAccommodationId(UUID id);
    Optional<Accommodation>findByName(String name);
    Optional<Accommodation>findByNameAndAddress_CityAndAddress_Street(String name, String city, String address);
    Optional<List<Accommodation>>findByType(AccommodationType type);
    void deleteAccommodationByAccommodationId(UUID accommodationId);

}
