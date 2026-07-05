package com.kipu.backend.rnc.application.queries;

/**
 * Query to retrieve all RNCs associated with a specific project.
 */
public record GetAllNonConformityRecordsByProjectIdQuery(String projectId) {
}