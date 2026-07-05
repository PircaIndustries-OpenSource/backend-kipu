package com.kipu.backend.rnc.application.commands;

import java.util.List;

/**
 * Command to create a new Non-Conformance Record (RNC).
 */
public record CreateNonConformityRecordCommand(
        String projectId,
        String title,
        String description,
        String specialty,
        String location,
        String severity,
        String reportedBy,
        List<String> images
) {
}