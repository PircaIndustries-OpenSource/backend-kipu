package com.kipu.backend.rnc.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Resource DTO for creating a new Non-Conformance Record.
 */
public record CreateNonConformityRecordResource(
        @NotBlank(message = "Project ID is mandatory") String projectId,
        @NotBlank(message = "Title is mandatory") String title,
        @NotBlank(message = "Description is mandatory") String description,
        @NotBlank(message = "Specialty is mandatory") String specialty,
        @NotBlank(message = "Location is mandatory") String location,
        @NotBlank(message = "Severity is mandatory") String severity,
        @NotBlank(message = "Reporter ID is mandatory") String reportedBy,
        List<String> images
) {
}