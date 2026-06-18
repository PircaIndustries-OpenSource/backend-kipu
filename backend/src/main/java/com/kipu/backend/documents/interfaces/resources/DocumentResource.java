package com.kipu.backend.documents.interfaces.resources;

import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "Represents a document and its digital signature status")
public record DocumentResource(
        @Schema(description = "Unique identifier of the document", example = "doc-998877")
        String id,

        @Schema(description = "Type or category of the document", example = "Plano de Cimentación")
        String type,

        @Schema(description = "Indicates if the document has been fully signed by all required parties", example = "false")
        boolean isSigned,

        @Schema(description = "Cryptographic token or hash representing the digital signature, if signed", example = "SIGN-167888999")
        String digitalSignatureToken,

        @Schema(description = "Deadline for the document signature", example = "2026-12-31T23:59:59")
        LocalDateTime deadLine,

        @Schema(description = "Unique identifier of the associated project", example = "proj-01")
        String projectId,

        @Schema(description = "List of signers assigned to this document")
        List<SignerResource> signers
) {}