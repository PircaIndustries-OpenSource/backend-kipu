package com.kipu.backend.Logistics.machinery.domain.model.valueobjects;

import java.util.regex.Pattern;

public record MachineryAssignmentDetail(String value) {
    private static final int MAX_LENGTH = 500;
    private static final String PATTERN = "^[a-zA-ZáéíóúñÑüÜ\\s\\-_0-9.,;:()]+$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);

    private static final String NOT_BLANK_MESSAGE_KEY = "machinery.error.assignmentDetail.notBlank";
    private static final String SIZE_MESSAGE_KEY = "machinery.error.assignmentDetail.size";
    private static final String PATTERN_MESSAGE_KEY = "machinery.error.assignmentDetail.pattern";

    public MachineryAssignmentDetail {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
        if (!COMPILED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return value();
    }
}
