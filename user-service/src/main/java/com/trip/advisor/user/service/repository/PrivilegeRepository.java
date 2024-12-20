package com.trip.advisor.user.service.repository;

import com.trip.advisor.user.service.model.entity.Privilege;
import com.trip.advisor.user.service.model.enums.EPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(EPrivilege name);
}
