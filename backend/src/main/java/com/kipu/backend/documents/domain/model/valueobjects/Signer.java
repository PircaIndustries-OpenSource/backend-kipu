package com.kipu.backend.documents.domain.model.valueobjects;

import com.kipu.backend.shared.domain.exceptions.BusinessException;

public record Signer(String teamUserId, String fullName) {
    public Signer {
        if (teamUserId.isBlank()) throw new BusinessException("document.validation.invalidUserId");
        if (fullName == null || fullName.isBlank()) throw new BusinessException("document.validation.emptyUserName");

    }
}