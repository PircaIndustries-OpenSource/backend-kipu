package com.kipu.backend.teamusers.domain.model.exceptions;

public class FullNameBlankOrNullException extends RuntimeException {

    public FullNameBlankOrNullException() {
        super("Fullname cannot be blank or null");
    }
}
