package com.kipu.backend.rnc.domain.repositories;

import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.domain.model.valueobjects.RncStatus;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the NonConformityRecord aggregate.
 */
public interface NonConformityRecordRepository {
    NonConformityRecord save(NonConformityRecord nonConformityRecord);
    Optional<NonConformityRecord> findById(String id);
    List<NonConformityRecord> findAllByProjectId(ProjectId projectId);
    List<NonConformityRecord> findAllByProjectIdAndStatus(ProjectId projectId, RncStatus status);
}