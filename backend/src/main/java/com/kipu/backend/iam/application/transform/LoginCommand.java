package com.kipu.backend.iam.application.transform;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Command representing a login request.
 */
public record LoginCommand(
        @NotBlank(message = "{iam.validation.email.required}")
        @Email(message = "{iam.validation.email.format}")
        String email,

        @NotBlank(message = "{iam.validation.password.required}")
        String password
) {}
