package com.trip.advisor.accommodation.service.repository;

import com.trip.advisor.accommodation.service.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    void deleteReservationByAccommodationId(UUID accommodationId);

    @Query("SELECT r FROM Reservation r WHERE r.accommodationId = :accommodationId AND " +
            "((:startDate BETWEEN r.startDate AND r.endDate) OR " +
            "(:endDate BETWEEN r.startDate AND r.endDate) OR " +
            "(r.startDate BETWEEN :startDate AND :endDate))")
    List<Reservation> findOverlappingReservations(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("accommodationId") UUID accommodationId);


    Optional<Reservation> findById(Long id);
}
