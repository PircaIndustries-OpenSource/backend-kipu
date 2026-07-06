package com.kipu.backend.documents.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to send a verification code for document signing")
public record SendSignCodeRequest(
        @Schema(description = "Email of the user requesting the code", example = "usuario@kipu.com")
        @NotBlank(message = "{document.validation.emailRequired}")
        @Email(message = "{document.validation.emailFormat}")
        String email,

        @Schema(description = "Team user ID of the signer requesting the code", example = "us-12345")
        @NotBlank(message = "{document.validation.invalidUserId}")
        String teamUserId
) {}
