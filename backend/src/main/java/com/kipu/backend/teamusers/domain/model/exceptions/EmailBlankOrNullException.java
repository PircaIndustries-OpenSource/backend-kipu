package com.kipu.backend.teamusers.domain.model.exceptions;

public class EmailBlankOrNullException extends RuntimeException{

    public EmailBlankOrNullException() {
        super("user.validation.emailBlank");
    }

}
