package com.kipu.backend.documents.interfaces.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record CreateDocumentResource(
        @NotBlank(message = "The document type cannot be blank")
        String type,

        @NotNull(message = "The deadline is required")
        @Future(message = "The deadline must be a date in the future")
        LocalDateTime deadLine,

        @NotBlank(message = "The project ID cannot be blank")
        String projectId,

        @NotNull(message = "The signers list cannot be null")
        List<@Valid SignerResource> signers
) {}