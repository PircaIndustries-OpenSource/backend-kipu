package com.kipu.backend.rnc.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.rnc.domain.model.valueobjects.RncStatus;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.entities.NonConformityRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for NonConformityRecord persistence.
 */
@Repository
public interface NonConformityRecordJpaRepository extends JpaRepository<NonConformityRecordJpaEntity, String> {
    List<NonConformityRecordJpaEntity> findAllByProjectId(String projectId);
    List<NonConformityRecordJpaEntity> findAllByProjectIdAndStatus(String projectId, RncStatus status);
}