package com.kipu.backend.documents.infraestructure.persistence.jpa.adapters;

import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import com.kipu.backend.documents.infraestructure.persistence.jpa.entities.DocumentJpaEntity;
import com.kipu.backend.documents.infraestructure.persistence.jpa.mappers.DocumentMapper;
import com.kipu.backend.documents.infraestructure.persistence.jpa.repositories.DocumentJpaRepository;
import com.kipu.backend.shared.domain.exceptions.BusinessException;
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
        var entity = jpaRepository.findById(id);
        if (entity.isEmpty()) {
            throw new BusinessException("document.validation.noDocumentFound");
        }
        return Optional.of(mapper.toDomain(entity.get()));
    }

    @Override
    public List<Document> findByProjectId(String projectId) {
        var documents = jpaRepository.findByProjectId(projectId);
        return documents.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Document> findByIsSignedTrue(String projectId) {
        return this.jpaRepository.findByIsSignedTrue(projectId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Document> findByIsSignedFalse(String projectId) {
        return this.jpaRepository.findByIsSignedFalse(projectId).stream().map(mapper::toDomain).toList();
    }
}
