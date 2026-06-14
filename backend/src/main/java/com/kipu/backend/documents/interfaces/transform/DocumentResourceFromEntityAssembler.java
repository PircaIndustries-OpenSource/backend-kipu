package com.kipu.backend.documents.interfaces.transform;

import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import com.kipu.backend.documents.interfaces.resources.DocumentResource;
import com.kipu.backend.documents.interfaces.resources.SignerResource;

import java.util.List;

public class DocumentResourceFromEntityAssembler {
    public static DocumentResource toResource(Document document) {

        List<SignerResource> signers = document.getSigners().stream().map(s ->
                new SignerResource(s.teamUserId(), s.fullName())).toList();
        return new DocumentResource(
                document.getId(),
                document.getType(),
                document.isSigned(),
                document.getDigitalSignatureToken(),
                document.getDeadLine(),
                document.getProjectId(),
                signers
        );
    }
}
