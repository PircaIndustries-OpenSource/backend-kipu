package com.kipu.backend.documents.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Represents a user required to sign a document")
public record SignerResource(
        @Schema(description = "Unique identifier of the team user required to sign", example = "us-12345")
        @NotBlank(message = "{document.validation.invalidUserId}")
        String teamUserId,

        @Schema(description = "Full name of the signer", example = "Ing. Martin Suarez")
        @NotBlank(message = "{document.validation.emptyUserName}")
        String fullName
) {}