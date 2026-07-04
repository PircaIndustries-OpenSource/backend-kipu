package com.kipu.backend.documents.application.commands;

public record SignDocumentCommand(
        String id,
        String code,
        String email,
        String teamUserId,
        String fullName
) {
    public SignDocumentCommand {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("document.validation.idRequired");
        if (code == null || code.isBlank()) throw new IllegalArgumentException("document.validation.codeRequired");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("document.validation.emailRequired");
        if (teamUserId == null || teamUserId.isBlank()) throw new IllegalArgumentException("document.validation.invalidUserId");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("document.validation.emptyUserName");
    }
}
