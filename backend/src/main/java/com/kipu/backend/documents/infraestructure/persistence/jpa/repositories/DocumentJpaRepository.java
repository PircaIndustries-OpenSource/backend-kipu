package com.kipu.backend.documents.infraestructure.persistence.jpa.repositories;

import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.infraestructure.persistence.jpa.entities.DocumentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentJpaRepository extends JpaRepository<DocumentJpaEntity, String> {
    List<DocumentJpaEntity> findByProjectId(String projectId);
    List<DocumentJpaEntity> findByIsSignedTrue(String projectId);
    List<DocumentJpaEntity> findByIsSignedFalse(String projectId);
}
