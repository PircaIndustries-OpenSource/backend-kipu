package com.kipu.backend.Logistics.domain.model.valueobjects;

import java.util.regex.Pattern;

public record SocialReason(String value) {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 40;
    private static final String PATTERN = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚüÜ .,&\\-]+$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);

    private static final String NOT_BLANK_MESSAGE_KEY = "social.reason.error.socialReason.notBlank";
    private static final String MIN_SIZE_MESSAGE_KEY = "social.reason.error.socialReason.minSize";
    private static final String MAX_SIZE_MESSAGE_KEY = "social.reason.error.socialReason.maxSize";
    private static final String PATTERN_MESSAGE_KEY = "social.reason.error.socialReason.pattern";

    public SocialReason {
        String trimmedValue = value != null ? value.trim() : null;

        if (trimmedValue == null || trimmedValue.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }

        if (trimmedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(MIN_SIZE_MESSAGE_KEY);
        }

        if (trimmedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(MAX_SIZE_MESSAGE_KEY);
        }

        if (!COMPILED_PATTERN.matcher(trimmedValue).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return value();
    }
}