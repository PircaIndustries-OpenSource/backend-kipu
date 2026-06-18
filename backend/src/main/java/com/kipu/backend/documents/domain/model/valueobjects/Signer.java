package com.kipu.backend.documents.domain.model.valueobjects;

public record Signer(String teamUserId, String fullName) {
    public Signer {
        if (teamUserId.isBlank()) throw new IllegalArgumentException("document.validation.invalidUserId");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("document.validation.emptyUserName");

    }
}