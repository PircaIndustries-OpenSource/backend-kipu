package com.kipu.backend.documents.domain.model.valueobjects;

public record Signer(String teamUserId, String fullName) {
    public Signer {
        if (teamUserId.isBlank()) throw new IllegalArgumentException("El ID del usuario es inválido");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("El nombre del usuario no puede estar vacío");

    }
}