package com.kipu.backend.teamusers.domain.model.exceptions;

public class EmailBlankOrNullException extends RuntimeException{

    public EmailBlankOrNullException() {
        super("Email cannot be blank or null");
    }

}
