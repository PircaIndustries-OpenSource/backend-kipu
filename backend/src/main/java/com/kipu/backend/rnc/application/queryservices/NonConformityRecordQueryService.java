package com.kipu.backend.rnc.application.queryservices;

import com.kipu.backend.rnc.application.queries.GetAllNonConformityRecordsByProjectIdQuery;
import com.kipu.backend.rnc.application.queries.GetNonConformityRecordByIdQuery;
import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining the query operations for NonConformityRecord.
 */
public interface NonConformityRecordQueryService {
    Optional<NonConformityRecord> handle(GetNonConformityRecordByIdQuery query);
    List<NonConformityRecord> handle(GetAllNonConformityRecordsByProjectIdQuery query);
}