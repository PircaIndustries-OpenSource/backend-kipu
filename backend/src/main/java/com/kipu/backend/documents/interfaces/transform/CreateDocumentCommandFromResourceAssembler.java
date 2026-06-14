package com.kipu.backend.documents.interfaces.transform;

import com.kipu.backend.documents.application.commands.CreateDocumentCommand;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import com.kipu.backend.documents.interfaces.resources.CreateDocumentResource;
import com.kipu.backend.documents.interfaces.resources.SignerResource;

import java.util.List;

public class CreateDocumentCommandFromResourceAssembler {
    public static CreateDocumentCommand toCommand(CreateDocumentResource resource) {

        List<Signer> signers = resource.signers().stream().map(s ->
                new Signer(s.teamUserId(), s.fullName())).toList();

        return new CreateDocumentCommand(
                resource.type(),
                resource.deadLine(),
                resource.projectId(),
                signers
        );
    }
}
