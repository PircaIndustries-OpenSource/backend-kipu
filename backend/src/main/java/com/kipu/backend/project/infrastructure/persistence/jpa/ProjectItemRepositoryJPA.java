package com.kipu.backend.project.infrastructure.persistence.jpa;

import com.kipu.backend.project.domain.model.entities.ProjectItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository interface for ProjectItem entities.
 */
@Repository
public interface ProjectItemRepositoryJPA extends JpaRepository<ProjectItem, Long> {
    List<ProjectItem> findByProjectId(String projectId);
}
