package com.kipu.backend.rnc.application.internal.queryservices;

import com.kipu.backend.rnc.application.queries.GetAllNonConformityRecordsByProjectIdQuery;
import com.kipu.backend.rnc.application.queries.GetNonConformityRecordByIdQuery;
import com.kipu.backend.rnc.application.queryservices.NonConformityRecordQueryService;
import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.rnc.domain.repositories.NonConformityRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service for NonConformityRecord.
 */
@Service
public class NonConformityRecordQueryServiceImpl implements NonConformityRecordQueryService {

    private final NonConformityRecordRepository repository;

    public NonConformityRecordQueryServiceImpl(NonConformityRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<NonConformityRecord> handle(GetNonConformityRecordByIdQuery query) {
        return repository.findById(query.rncId());
    }

    @Override
    public List<NonConformityRecord> handle(GetAllNonConformityRecordsByProjectIdQuery query) {
        return repository.findAllByProjectId(new ProjectId(query.projectId()));
    }
}