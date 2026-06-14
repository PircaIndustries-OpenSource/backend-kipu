package com.kipu.backend.project.infrastructure.persistence.jpa;

import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.repositories.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter implementing the ProjectRepository domain interface.
 */
@Component
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectRepositoryJPA repositoryJPA;

    public ProjectRepositoryImpl(ProjectRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }

    @Override
    public Project save(Project project) {
        return repositoryJPA.save(project);
    }

    @Override
    public Optional<Project> findById(String id) {
        return repositoryJPA.findById(id);
    }

    @Override
    public Optional<Project> findByName(String name) {
        return repositoryJPA.findByName(name);
    }

    @Override
    public List<Project> findAll() {
        return repositoryJPA.findAll();
    }

    @Override
    public List<Project> findByCreatedBy(String username) {
        return repositoryJPA.findByCreatedBy(username);
    }

    @Override
    public boolean existsByName(String name) {
        return repositoryJPA.existsByName(name);
    }
}
