package com.kipu.backend.Logistics.domain.model.valueobjects;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final int MAX_LENGTH = 254;
    private static final String PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);
    private static final String NOT_BLANK_MESSAGE_KEY = "email.error.email.notBlank";
    private static final String SIZE_MESSAGE_KEY = "email.error.email.size";
    private static final String PATTERN_MESSAGE_KEY = "email.error.email.pattern";
    public Email{
        String trimmedValue = value != null ? value.trim() : null;

        if (trimmedValue == null || trimmedValue.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
        if (!COMPILED_PATTERN.matcher(trimmedValue).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }
}
