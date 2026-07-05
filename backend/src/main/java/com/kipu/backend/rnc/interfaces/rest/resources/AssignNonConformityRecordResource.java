package com.kipu.backend.rnc.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Resource DTO for assigning an RNC to a user.
 */
public record AssignNonConformityRecordResource(
        @NotBlank(message = "Assignee ID is mandatory") String assigneeId
) {
}