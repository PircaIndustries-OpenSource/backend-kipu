package com.kipu.backend.Logistics.machinery.domain.model.valueobjects;

public record MachineryMaintenanceHours(String value) {
    private static final String NOT_BLANK_MESSAGE_KEY = "machinery.error.maintenanceHours.notBlank";
    private static final String INVALID_MESSAGE_KEY = "machinery.error.maintenanceHours.invalid";

    public MachineryMaintenanceHours {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        try {
            int hours = Integer.parseInt(value);
            if (hours < 0) {
                throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return value();
    }
}
