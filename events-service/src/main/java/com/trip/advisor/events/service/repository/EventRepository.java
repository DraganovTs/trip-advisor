package com.trip.advisor.events.service.repository;

import com.trip.advisor.events.service.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    Optional<List<Event>> findAllByDate(LocalDate date);

    Optional<List<Event>> findAllByCity(String city);

    Optional<List<Event>> findAllByCityAndDateBetween(String city, LocalDate start, LocalDate end);
}
