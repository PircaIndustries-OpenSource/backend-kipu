package com.kipu.backend.documents.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "Payload to create and distribute a new document requiring digital signatures")
public record CreateDocumentResource(
        @Schema(description = "Type or category of the document (e.g., Contract, Blueprint)", example = "Plano de Cimentación")
        @NotBlank(message = "{document.validation.typeRequired}")
        String type,

        @Schema(description = "Deadline for the document to be completely signed", example = "2026-12-31T23:59:59")
        @NotNull(message = "{document.validation.deadlineRequired}")
        @Future(message = "{document.validation.deadlineFuture}")
        LocalDateTime deadLine,

        @Schema(description = "Unique identifier of the project this document belongs to", example = "proj-01")
        @NotBlank(message = "{document.validation.projectIdRequired}")
        String projectId,

        @Schema(description = "List of users required to sign this document")
        @NotNull(message = "{document.validation.signersNotNull}")
        List<@Valid SignerResource> signers
) {}