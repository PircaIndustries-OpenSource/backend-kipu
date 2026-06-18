package com.kipu.backend.documents.application.internal.commandservices;

import com.kipu.backend.documents.application.commands.CreateDocumentCommand;
import com.kipu.backend.documents.application.commands.SignDocumentCommand;
import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;

import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentCommandServiceImpl implements DocumentCommandService {

    private final DocumentRepository documentRepository;
    private final MessageSource messageSource;

    public DocumentCommandServiceImpl(DocumentRepository documentRepository, MessageSource messageSource) {
        this.documentRepository = documentRepository;
        this.messageSource = messageSource;
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
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage(
                            "document.validation.documentNotFound",
                            new Object[]{command.id()},
                            LocaleContextHolder.getLocale()
                    );
                    return new IllegalArgumentException(errorMessage);
                });

        document.markAsSigned();

        return Optional.of(this.documentRepository.save(document));
    }
}
