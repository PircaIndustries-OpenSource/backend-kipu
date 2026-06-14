package com.kipu.backend.project.application.transform;

import jakarta.validation.constraints.NotBlank;

/**
 * Command representing status update payload.
 */
public record UpdateProjectStatusCommand(
        @NotBlank(message = "{project.validation.status.required}")
        String status,

        String statusJustification
) {}
