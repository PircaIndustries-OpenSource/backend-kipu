package com.kipu.backend.project.infrastructure.persistence.jpa;

import com.kipu.backend.project.domain.model.aggregates.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository interface for Project entities.
 */
@Repository
public interface ProjectRepositoryJPA extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    List<Project> findByCreatedBy(String createdBy);
    boolean existsByName(String name);
}
