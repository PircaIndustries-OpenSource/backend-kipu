package com.kipu.backend.documents.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Schema(description = "Represents a user required to sign a document")
public record SignerResource(
        @Schema(description = "Unique identifier of the team user required to sign", example = "us-12345")
        @NotBlank(message = "{document.validation.invalidUserId}")
        String teamUserId,

        @Schema(description = "Full name of the signer", example = "Ing. Martin Suarez")
        @NotBlank(message = "{document.validation.emptyUserName}")
        String fullName,

        @Schema(description = "Date and time when the signer signed", example = "2026-07-03T10:30:00")
        LocalDateTime signedAt
) {}
