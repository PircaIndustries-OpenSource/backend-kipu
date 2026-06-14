package com.kipu.backend.Logistics.domain.model.valueobjects;

import java.util.regex.Pattern;

public record Ruc(String value) {
    private static final int LENGTH = 11;
    private static final String PATTERN = "^\\d{11}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);

    private static final String NOT_BLANK_MESSAGE_KEY = "ruc.error.ruc.notBlank";
    private static final String SIZE_MESSAGE_KEY = "ruc.error.ruc.size";
    private static final String PATTERN_MESSAGE_KEY = "ruc.error.ruc.pattern";
    private static final String INVALID_SUNAT_MESSAGE_KEY = "ruc.error.ruc.invalidSunat";

    public Ruc {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }

        if (value.length() != LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }

        if (!COMPILED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }

        if (!isValidSunatRuc(value)) {
            throw new IllegalArgumentException(INVALID_SUNAT_MESSAGE_KEY);
        }
    }

    private static boolean isValidSunatRuc(String ruc) {
        if (!ruc.startsWith("10") && !ruc.startsWith("15") && !ruc.startsWith("17") && !ruc.startsWith("20")) {
            return false;
        }

        int[] multipliers = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += (ruc.charAt(i) - '0') * multipliers[i];
        }

        int remainder = sum % 11;
        int checkDigit = 11 - remainder;
        if (checkDigit == 10) checkDigit = 0;
        if (checkDigit == 11) checkDigit = 1;

        return checkDigit == (ruc.charAt(10) - '0');
    }
    @Override
    public String toString() {
        return value();
    }
}