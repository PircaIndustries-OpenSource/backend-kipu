package com.kipu.backend.rnc.application.queries;

/**
 * Query to retrieve a specific RNC by its unique ID.
 */
public record GetNonConformityRecordByIdQuery(String rncId) {
}