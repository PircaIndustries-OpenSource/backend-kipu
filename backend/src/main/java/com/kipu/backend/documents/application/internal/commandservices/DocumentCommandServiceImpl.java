package com.kipu.backend.documents.application.internal.commandservices;

import com.kipu.backend.documents.application.commands.CreateDocumentCommand;
import com.kipu.backend.documents.application.commands.SendSignCodeCommand;
import com.kipu.backend.documents.application.commands.SignDocumentCommand;
import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import com.kipu.backend.iam.application.internal.outboundservices.email.EmailService;
import com.kipu.backend.iam.application.internal.outboundservices.otp.OtpService;
import com.kipu.backend.shared.domain.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentCommandServiceImpl implements DocumentCommandService {

    private final DocumentRepository documentRepository;
    private final OtpService otpService;
    private final EmailService emailService;

    public DocumentCommandServiceImpl(DocumentRepository documentRepository,
                                      OtpService otpService,
                                      EmailService emailService) {
        this.documentRepository = documentRepository;
        this.otpService = otpService;
        this.emailService = emailService;
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
    public void handle(SendSignCodeCommand command) {
        Document document = documentRepository.findById(command.documentId())
                .orElseThrow(() -> new BusinessException("document.validation.documentNotFound"));
        boolean isSigner = document.getSigners().stream()
                .anyMatch(s -> s.teamUserId().equals(command.teamUserId()));
        if (!isSigner) {
            throw new BusinessException("document.validation.userNotSigner");
        }
        otpService.generateAndSendOtp(command.email());
    }

    @Override
    @Transactional
    public Optional<Document> handle(SignDocumentCommand command) {
        boolean valid = otpService.validateOtp(command.email(), command.code());
        if (!valid) {
            throw new BusinessException("document.validation.invalidOrExpiredCode");
        }

        Document document = documentRepository.findById(command.id())
                .orElseThrow(() -> new BusinessException("document.validation.documentNotFound"));

        document.signAs(command.teamUserId());

        return Optional.of(documentRepository.save(document));
    }
}
