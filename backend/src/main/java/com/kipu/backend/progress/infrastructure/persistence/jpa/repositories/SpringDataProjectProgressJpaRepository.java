package com.kipu.backend.progress.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.progress.infrastructure.persistence.jpa.entities.ProjectProgressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataProjectProgressJpaRepository extends JpaRepository<ProjectProgressJpaEntity, Long> {
    List<ProjectProgressJpaEntity> findByProjectId(String projectId);
    List<ProjectProgressJpaEntity> findByParentId(Long parentId);
}