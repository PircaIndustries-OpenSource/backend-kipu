package com.kipu.backend.teamusers.domain.model.exceptions;

public class EmailInvalidException extends RuntimeException{

    public EmailInvalidException(String email) {
        super("Formato de email inválido: " + email);
    }
}
