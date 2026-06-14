package com.kipu.backend.project.application.internal.queryservices;

import com.kipu.backend.project.application.queryservices.ProjectQueryService;
import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.model.entities.ProjectItem;
import com.kipu.backend.project.domain.repositories.ProjectItemRepository;
import com.kipu.backend.project.domain.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for handling Project-related queries.
 */
@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;
    private final ProjectItemRepository projectItemRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository,
                                   ProjectItemRepository projectItemRepository) {
        this.projectRepository = projectRepository;
        this.projectItemRepository = projectItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> handleGetProjectById(String id) {
        return projectRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> handleGetAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> handleGetProjectsByCreatedBy(String username) {
        return projectRepository.findByCreatedBy(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectItem> handleGetProjectItemsByProjectId(String projectId) {
        return projectItemRepository.findByProjectId(projectId);
    }
}
