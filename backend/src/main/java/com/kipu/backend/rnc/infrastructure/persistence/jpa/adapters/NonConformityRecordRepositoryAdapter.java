package com.kipu.backend.rnc.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.domain.model.valueobjects.RncStatus;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.rnc.domain.repositories.NonConformityRecordRepository;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.entities.NonConformityRecordJpaEntity;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.mappers.NonConformityRecordMapper;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.repositories.NonConformityRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the domain repository contract.
 * Keeps the domain layer decoupled from Spring Data JPA.
 */
@Component
public class NonConformityRecordRepositoryAdapter implements NonConformityRecordRepository {

    private final NonConformityRecordJpaRepository repository;

    public NonConformityRecordRepositoryAdapter(NonConformityRecordJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public NonConformityRecord save(NonConformityRecord nonConformityRecord) {
        NonConformityRecordJpaEntity entity = NonConformityRecordMapper.toEntity(nonConformityRecord);
        NonConformityRecordJpaEntity savedEntity = repository.save(entity);
        return NonConformityRecordMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<NonConformityRecord> findById(String id) {
        return repository.findById(id).map(NonConformityRecordMapper::toDomain);
    }

    @Override
    public List<NonConformityRecord> findAllByProjectId(ProjectId projectId) {
        return repository.findAllByProjectId(projectId.value()).stream()
                .map(NonConformityRecordMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<NonConformityRecord> findAllByProjectIdAndStatus(ProjectId projectId, RncStatus status) {
        return repository.findAllByProjectIdAndStatus(projectId.value(), status).stream()
                .map(NonConformityRecordMapper::toDomain)
                .collect(Collectors.toList());
    }
}