package com.kipu.backend.Logistics.domain.model.valueobjects;

import java.util.regex.Pattern;

public record Phone(String value) {
    private static final int MIN_LENGTH = 7;
    private static final int MAX_LENGTH = 15;
    private static final String PATTERN = "^\\+?[0-9]{7,15}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);

    private static final String NOT_BLANK_MESSAGE_KEY = "phone.error.phone.notBlank";
    private static final String MIN_SIZE_MESSAGE_KEY = "phone.error.phone.minSize";
    private static final String MAX_SIZE_MESSAGE_KEY = "phone.error.phone.maxSize";
    private static final String PATTERN_MESSAGE_KEY = "phone.error.phone.pattern";

    public Phone {
        String cleanedValue = cleanPhoneNumber(value);

        if (cleanedValue == null || cleanedValue.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }

        if (cleanedValue.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(MIN_SIZE_MESSAGE_KEY);
        }

        if (cleanedValue.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(MAX_SIZE_MESSAGE_KEY);
        }

        if (!COMPILED_PATTERN.matcher(cleanedValue).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }

    private static String cleanPhoneNumber(String value) {
        if (value == null) {
            return null;
        }
        return value.replace(" ", "")
                .replace("-", "")
                .replace("(", "")
                .replace(")", "")
                .trim();
    }
    @Override
    public String toString() {
        return value();
    }
}