package com.kipu.backend.project.domain.repositories;

import com.kipu.backend.project.domain.model.entities.ProjectItem;

import java.util.List;

/**
 * Pure domain repository contract for the ProjectItem entity.
 */
public interface ProjectItemRepository {
    ProjectItem save(ProjectItem projectItem);
    List<ProjectItem> saveAll(List<ProjectItem> projectItems);
    List<ProjectItem> findByProjectId(String projectId);
    void deleteByProjectId(String projectId);
}
