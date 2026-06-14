package com.kipu.backend.documents.interfaces.resources;

import jakarta.validation.constraints.NotBlank;

public record SignerResource(
        @NotBlank(message = "The team user ID is required")
        String teamUserId,

        @NotBlank(message = "The signer's full name is required")
        String fullName
) {}