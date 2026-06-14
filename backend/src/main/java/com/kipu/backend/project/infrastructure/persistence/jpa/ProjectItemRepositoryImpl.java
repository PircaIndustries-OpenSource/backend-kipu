package com.kipu.backend.project.infrastructure.persistence.jpa;

import com.kipu.backend.project.domain.model.entities.ProjectItem;
import com.kipu.backend.project.domain.repositories.ProjectItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Persistence adapter implementing the ProjectItemRepository domain interface.
 */
@Component
public class ProjectItemRepositoryImpl implements ProjectItemRepository {

    private final ProjectItemRepositoryJPA repositoryJPA;

    public ProjectItemRepositoryImpl(ProjectItemRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }

    @Override
    public ProjectItem save(ProjectItem projectItem) {
        return repositoryJPA.save(projectItem);
    }

    @Override
    public List<ProjectItem> saveAll(List<ProjectItem> projectItems) {
        return repositoryJPA.saveAll(projectItems);
    }

    @Override
    public List<ProjectItem> findByProjectId(String projectId) {
        return repositoryJPA.findByProjectId(projectId);
    }
}
