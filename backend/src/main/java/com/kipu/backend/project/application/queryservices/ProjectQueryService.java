package com.kipu.backend.project.application.queryservices;

import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.model.entities.ProjectItem;

import java.util.List;
import java.util.Optional;

/**
 * Service defining project query handling contracts.
 */
public interface ProjectQueryService {
    Optional<Project> handleGetProjectById(String id);
    List<Project> handleGetAllProjects();
    List<Project> handleGetProjectsByCreatedBy(String username);
    List<ProjectItem> handleGetProjectItemsByProjectId(String projectId);
}
