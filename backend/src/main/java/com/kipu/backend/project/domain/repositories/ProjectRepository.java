package com.kipu.backend.project.domain.repositories;

import com.kipu.backend.project.domain.model.aggregates.Project;

import java.util.List;
import java.util.Optional;

/**
 * Pure domain repository contract for the Project aggregate.
 */
public interface ProjectRepository {
    Project save(Project project);
    Optional<Project> findById(String id);
    Optional<Project> findByName(String name);
    List<Project> findAll();
    List<Project> findByCreatedBy(String username);
    boolean existsByName(String name);
    void deleteById(String id);
}
