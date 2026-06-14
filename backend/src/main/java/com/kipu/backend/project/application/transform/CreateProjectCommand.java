package com.kipu.backend.project.application.transform;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Command containing payload for registering a new construction project.
 */
public record CreateProjectCommand(
        @NotBlank(message = "{project.validation.name.required}")
        String name,

        String description,

        @NotBlank(message = "{project.validation.startDate.required}")
        String startDate,

        String endDate,

        @NotNull(message = "{project.validation.budget.required}")
        @Min(value = 0, message = "{project.validation.budget.min}")
        Double totalBudget,

        @NotBlank(message = "{project.validation.location.required}")
        String location,

        String imageUrl
) {}
