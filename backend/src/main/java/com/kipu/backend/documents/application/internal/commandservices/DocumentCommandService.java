package com.kipu.backend.documents.application.internal.commandservices;

import com.kipu.backend.documents.application.commands.CreateDocumentCommand;
import com.kipu.backend.documents.application.commands.SendSignCodeCommand;
import com.kipu.backend.documents.application.commands.SignDocumentCommand;
import com.kipu.backend.documents.domain.model.aggregates.Document;

import java.util.Optional;

public interface DocumentCommandService {
    Optional<Document> handle(CreateDocumentCommand command);
    Optional<Document> handle(SignDocumentCommand command);
    void handle(SendSignCodeCommand command);
}
