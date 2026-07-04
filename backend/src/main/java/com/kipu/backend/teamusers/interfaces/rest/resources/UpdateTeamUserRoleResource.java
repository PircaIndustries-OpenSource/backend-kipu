package com.kipu.backend.teamusers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update a team user's role")
public record UpdateTeamUserRoleResource(
        @Schema(description = "New role to assign", example = "Administrador")
        @NotBlank(message = "{user.validation.roleRequired}")
        String role
) {}
