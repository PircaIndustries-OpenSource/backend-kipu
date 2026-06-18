package com.kipu.backend.teamusers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload to invite or register a new user into a project team")
public record CreateTeamUserResource(
        @Schema(description = "Full name of the team user", example = "Lucia Gomez")
        @NotBlank(message = "{user.validation.fullNameRequired}")
        @Size(min = 3, max = 100, message = "{user.validation.fullNameSize}")
        String fullName,

        @Schema(description = "Valid email address of the team user", example = "lucia.gomez@kipu.com")
        @NotBlank(message = "user.validation.emailRequired")
        @Email(message = "{user.validation.emailFormat}")
        String email,

        @Schema(description = "Assigned role of the user within the platform", example = "Ingeniero Residente")
        @NotBlank(message = "{user.validation.roleRequired}")
        String role,

        @Schema(description = "Unique identifier of the project the user is invited to", example = "proj-01")
        @NotBlank(message = "{user.validation.projectIdRequired}")
        String projectId
) {}
