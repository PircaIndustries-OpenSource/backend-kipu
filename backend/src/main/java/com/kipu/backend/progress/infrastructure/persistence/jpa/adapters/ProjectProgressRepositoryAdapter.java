package com.kipu.backend.progress.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.progress.domain.model.aggregates.ProjectProgress;
import com.kipu.backend.progress.domain.repositories.ProjectProgressRepository;
import com.kipu.backend.progress.infrastructure.persistence.jpa.entities.ProjectProgressJpaEntity;
import com.kipu.backend.progress.infrastructure.persistence.jpa.mappers.ProjectProgressMapper;
import com.kipu.backend.progress.infrastructure.persistence.jpa.repositories.SpringDataProjectProgressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProjectProgressRepositoryAdapter implements ProjectProgressRepository {

    private final SpringDataProjectProgressJpaRepository jpaRepository;

    public ProjectProgressRepositoryAdapter(SpringDataProjectProgressJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProjectProgress save(ProjectProgress projectProgress) {
        ProjectProgressJpaEntity entity = ProjectProgressMapper.toJpaEntity(projectProgress);
        ProjectProgressJpaEntity saved = jpaRepository.save(entity);
        return ProjectProgressMapper.toDomain(saved);
    }

    @Override
    public Optional<ProjectProgress> findById(Long id) {
        return jpaRepository.findById(id).map(ProjectProgressMapper::toDomain);
    }

    @Override
    public List<ProjectProgress> findByProjectId(String projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(ProjectProgressMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectProgress> findByParentId(Long parentId) {
        return jpaRepository.findByParentId(parentId).stream()
                .map(ProjectProgressMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}