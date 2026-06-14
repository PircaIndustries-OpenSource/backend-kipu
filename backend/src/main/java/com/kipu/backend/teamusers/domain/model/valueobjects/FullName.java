package com.kipu.backend.teamusers.domain.model.valueobjects;

import com.kipu.backend.teamusers.domain.model.exceptions.FullNameBlankOrNullException;

public record FullName(String fullName) {

    public FullName {
        if (fullName == null || fullName.isBlank()) {
            throw new FullNameBlankOrNullException();
        }
    }


}
