package com.kipu.backend.documents.application.commands;

public record SendSignCodeCommand(String documentId, String email) {
    public SendSignCodeCommand {
        if (documentId == null || documentId.isBlank())
            throw new IllegalArgumentException("document.validation.idRequired");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("document.validation.emailRequired");
    }
}
