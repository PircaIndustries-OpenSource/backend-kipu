package com.kipu.backend.teamusers.domain.model.exceptions;

public class FullNameBlankOrNullException extends RuntimeException {

    public FullNameBlankOrNullException() {
        super("user.validation.fullNameBlank");
    }
}
