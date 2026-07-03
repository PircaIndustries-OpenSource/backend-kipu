package com.kipu.backend.Logistics.machinery.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public record MachineryAssignedTo(@JsonValue String value) {
    private static final int MAX_LENGTH = 100;

    private static final String SIZE_MESSAGE_KEY = "machinery.error.assignedTo.size";

    public MachineryAssignedTo {
        if (value != null && value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }

    public boolean isAssigned() {
        return value != null && !value.isBlank();
    }

    @Override
    public String toString() {
        return value != null ? value : "";
    }
}
