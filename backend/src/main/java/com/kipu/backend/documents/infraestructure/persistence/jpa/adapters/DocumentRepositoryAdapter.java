package com.kipu.backend.documents.infraestructure.persistence.jpa.adapters;

import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import com.kipu.backend.documents.infraestructure.persistence.jpa.entities.DocumentJpaEntity;
import com.kipu.backend.documents.infraestructure.persistence.jpa.mappers.DocumentMapper;
import com.kipu.backend.documents.infraestructure.persistence.jpa.repositories.DocumentJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRepositoryAdapter implements DocumentRepository {

    private final DocumentJpaRepository jpaRepository;
    private final DocumentMapper mapper;


    public DocumentRepositoryAdapter(DocumentJpaRepository jpaRepository, DocumentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Document save(Document document) {
        DocumentJpaEntity entity = mapper.toJpa(document);
        return mapper.toDomain(jpaRepository.save(entity));

    }

    @Override
    public Optional<Document> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Document> findByProjectId(String projectId) {
        var documents = jpaRepository.findByProjectId(projectId);
        return documents.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Document> findByProjectIdAndIsSignedTrue(String projectId) {
        return this.jpaRepository.findByProjectIdAndIsSignedTrue(projectId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Document> findByProjectIdAndIsSignedFalse(String projectId) {
        return this.jpaRepository.findByProjectIdAndIsSignedFalse(projectId).stream().map(mapper::toDomain).toList();
    }
}
