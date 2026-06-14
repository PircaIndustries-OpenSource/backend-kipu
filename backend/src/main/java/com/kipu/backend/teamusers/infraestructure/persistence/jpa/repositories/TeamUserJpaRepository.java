package com.kipu.backend.teamusers.infraestructure.persistence.jpa.repositories;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.entities.TeamUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TeamUserJpaRepository extends JpaRepository<TeamUserJpaEntity, Long>,
        JpaSpecificationExecutor<TeamUserJpaEntity> {
    List<TeamUserJpaEntity> findByIsActiveTrue(String projectId);
    List<TeamUserJpaEntity> findByProjectId(String projectId);
    Optional<TeamUserJpaEntity> findById(String id);
    Optional<TeamUserJpaEntity> findByEmailAndProjectId(String email, String projectId);
}
