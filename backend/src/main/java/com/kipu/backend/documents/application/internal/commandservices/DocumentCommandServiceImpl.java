package com.kipu.backend.documents.application.internal.commandservices;

import com.kipu.backend.documents.application.commands.CreateDocumentCommand;
import com.kipu.backend.documents.application.commands.SignDocumentCommand;
import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentCommandServiceImpl implements DocumentCommandService {

    private final DocumentRepository documentRepository;

    public DocumentCommandServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public Optional<Document> handle(CreateDocumentCommand command) {
        String documentId = "doc-" + System.currentTimeMillis();
        Document document = new Document(documentId, command.type(), command.deadline(), command.projectId());

        for (Signer user : command.assignedUsers()) {
            document.assignSigner(user.teamUserId(), user.fullName());
        }

        return Optional.of(documentRepository.save(document));
    }

    @Override
    @Transactional
    public Optional<Document> handle(SignDocumentCommand command) {
        Document document = this.documentRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + command.id() + " was not found"));
        document.markAsSigned();

        return Optional.of(this.documentRepository.save(document));
    }
}
