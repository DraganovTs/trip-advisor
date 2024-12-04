package com.trip.advisor.user.service.repository;

import com.trip.advisor.user.service.model.entity.Role;
import com.trip.advisor.user.service.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
