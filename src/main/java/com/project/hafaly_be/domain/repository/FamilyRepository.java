package com.project.hafaly_be.domain.repository;

import com.project.hafaly_be.domain.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Boolean existsByCode(String code);

    Optional<Family> findByCode(String familyCode);
}
