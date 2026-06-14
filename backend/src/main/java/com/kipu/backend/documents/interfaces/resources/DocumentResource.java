package com.kipu.backend.documents.interfaces.resources;

import com.kipu.backend.documents.domain.model.valueobjects.Signer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record DocumentResource(
        String id,
        String type,
        boolean isSigned,
        String digitalSignatureToken,
        LocalDateTime deadLine,
        String projectId,
        List<SignerResource> signers
        ) {
}
