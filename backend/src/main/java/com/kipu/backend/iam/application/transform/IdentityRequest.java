package com.kipu.backend.iam.application.transform;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Command representing user registration or identity creation request from the frontend.
 */
public record IdentityRequest(
        @NotBlank(message = "{iam.validation.username.required}")
        @Size(min = 3, max = 50, message = "{iam.validation.username.size}")
        String name,

        @NotBlank(message = "{iam.validation.email.required}")
        @Email(message = "{iam.validation.email.format}")
        String email,

        @NotBlank(message = "{iam.validation.password.required}")
        @Size(min = 6, message = "{iam.validation.password.size}")
        String password,

        @NotBlank(message = "{iam.validation.role.required}")
        String role
) {
    /**
     * Map frontend IdentityRequest to RegisterUserCommand.
     */
    public RegisterUserCommand toRegisterUserCommand() {
        return new RegisterUserCommand(name, email, password, role);
    }
}
