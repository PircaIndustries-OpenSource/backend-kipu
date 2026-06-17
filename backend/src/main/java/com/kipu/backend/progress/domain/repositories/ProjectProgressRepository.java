package com.kipu.backend.progress.domain.repositories;

import com.kipu.backend.progress.domain.model.aggregates.ProjectProgress;

import java.util.List;
import java.util.Optional;

/**
 * Pure domain contract for ProjectProgress persistence operations.
 */
public interface ProjectProgressRepository {
    ProjectProgress save(ProjectProgress projectProgress);
    Optional<ProjectProgress> findById(Long id);
    List<ProjectProgress> findByProjectId(String projectId);
    List<ProjectProgress> findByParentId(Long parentId);
    void deleteById(Long id);
}