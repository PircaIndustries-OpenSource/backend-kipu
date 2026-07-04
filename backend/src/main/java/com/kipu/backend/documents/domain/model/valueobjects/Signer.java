package com.kipu.backend.documents.domain.model.valueobjects;

import com.kipu.backend.shared.domain.exceptions.BusinessException;

import java.time.LocalDateTime;

public class Signer {
    private final String teamUserId;
    private final String fullName;
    private LocalDateTime signedAt;

    public Signer(String teamUserId, String fullName) {
        if (teamUserId == null || teamUserId.isBlank()) throw new BusinessException("document.validation.invalidUserId");
        if (fullName == null || fullName.isBlank()) throw new BusinessException("document.validation.emptyUserName");
        this.teamUserId = teamUserId;
        this.fullName = fullName;
        this.signedAt = null;
    }

    public Signer(String teamUserId, String fullName, LocalDateTime signedAt) {
        this.teamUserId = teamUserId;
        this.fullName = fullName;
        this.signedAt = signedAt;
    }

    public String teamUserId() { return teamUserId; }
    public String fullName() { return fullName; }
    public LocalDateTime signedAt() { return signedAt; }

    public void markAsSigned() {
        this.signedAt = LocalDateTime.now();
    }

    public boolean isSigned() {
        return signedAt != null;
    }
}