package com.kipu.backend.teamusers.domain.model.valueobjects;

import com.kipu.backend.teamusers.domain.model.exceptions.EmailBlankOrNullException;
import com.kipu.backend.teamusers.domain.model.exceptions.EmailInvalidException;

public record EmailAddress(String address) {

    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new EmailBlankOrNullException();
        }
        if (!address.contains("@")) {
            throw new EmailInvalidException(address);
        }
    }

}
