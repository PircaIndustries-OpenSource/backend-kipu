package com.kipu.backend.project.application.transform;

import jakarta.validation.constraints.NotBlank;

/**
 * Command representing work item (Partida) creation payload.
 */
public record CreateProjectItemCommand(
        @NotBlank(message = "Partida name is required")
        String name,

        @NotBlank(message = "Start date is required")
        String startDate,

        @NotBlank(message = "End date is required")
        String endDate
) {}
