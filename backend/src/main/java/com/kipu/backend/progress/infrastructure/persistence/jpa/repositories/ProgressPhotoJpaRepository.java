package com.kipu.backend.progress.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.progress.infrastructure.persistence.jpa.entities.ProgressPhotoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressPhotoJpaRepository extends JpaRepository<ProgressPhotoJpaEntity, Long> {
    List<ProgressPhotoJpaEntity> findByProjectId(String projectId);
}
