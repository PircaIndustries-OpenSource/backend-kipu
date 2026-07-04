package com.kipu.backend.documents.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to sign a document with a verification code")
public record SignDocumentRequest(
        @Schema(description = "6-digit verification code sent to the user's email", example = "483921")
        @NotBlank(message = "{document.validation.codeRequired}")
        String code,

        @Schema(description = "Email of the signer", example = "usuario@kipu.com")
        @NotBlank(message = "{document.validation.emailRequired}")
        @Email(message = "{document.validation.emailFormat}")
        String email,

        @Schema(description = "Team user identifier of the signer", example = "a1b2c3d4")
        @NotBlank(message = "{document.validation.invalidUserId}")
        String teamUserId,

        @Schema(description = "Full name of the signer", example = "Juan Pérez")
        @NotBlank(message = "{document.validation.emptyUserName}")
        String fullName
) {}
