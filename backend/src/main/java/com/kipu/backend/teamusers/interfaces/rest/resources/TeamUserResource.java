package com.kipu.backend.teamusers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the detailed information of a platform user assigned to a project")
public record TeamUserResource(
        @Schema(description = "Unique identifier of the team user", example = "us-12345")
        String id,

        @Schema(description = "Full name of the team user", example = "Lucia Gomez")
        String fullName,

        @Schema(description = "Email address of the team user", example = "lucia.gomez@kipu.com")
        String email,

        @Schema(description = "Assigned role of the user", example = "Ingeniero Residente")
        String role,

        @Schema(description = "Indicates if the user account is currently active", example = "true")
        boolean isActive,

        @Schema(description = "Unique identifier of the associated project", example = "proj-01")
        String projectId
) {}