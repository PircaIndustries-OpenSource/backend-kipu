package com.kipu.backend.iam.application.transform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Command representing a request to modify a user's password.
 */
public record UpdateUserPasswordCommand(
        @NotBlank(message = "{iam.validation.password.required}")
        @Size(min = 6, message = "{iam.validation.password.size}")
        String password
) {}
