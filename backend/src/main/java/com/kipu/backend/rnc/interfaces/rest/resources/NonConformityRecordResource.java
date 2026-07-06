package com.kipu.backend.rnc.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

/**
 * Output Resource DTO representing the state of an RNC.
 */
public record NonConformityRecordResource(
        String id,
        String projectId,
        String title,
        String description,
        String specialty,
        String location,
        String severity,
        String status,
        String reportedBy,
        Date reportDate,
        List<String> images,
        String assignedTo,
        Date resolutionDate,
        List<SolutionLogResource> solutionNotes
) {
}