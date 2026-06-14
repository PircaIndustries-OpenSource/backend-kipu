package com.kipu.backend.documents.interfaces.resources;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record CreateDocumentResource(
        String type,
        LocalDateTime deadLine,
        String projectId,
        List<SignerResource> signers
) {
}
