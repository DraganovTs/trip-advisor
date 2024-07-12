package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {

    Optional<Accommodation>findByName(String name);
    Optional<Accommodation>findByNameAndAddress_CityAndAddress_Street(String name, String city, String address);


}
