package com.kipu.backend.iam.application.transform;

import jakarta.validation.constraints.NotBlank;

/**
 * Command representing a request to modify a user's role.
 */
public record UpdateUserRoleCommand(
        @NotBlank(message = "Role is required")
        String role
) {}
