package com.kipu.backend.teamusers.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTeamUserResource(
        @NotBlank(message = "The full name cannot be blank")
        @Size(min = 3, max = 100, message = "The full name must have between 3 and 100 characters")
        String fullName,

        @NotBlank(message = "The email cannot be blank")
        @Email(message = "The email format is invalid")
        String email,

        @NotBlank(message = "The role cannot be blank")
        String role,

        @NotBlank(message = "The projectId cannot be blank")
        String projectId
        ) {
}
