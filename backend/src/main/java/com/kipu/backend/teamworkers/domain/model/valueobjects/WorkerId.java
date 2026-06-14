package com.kipu.backend.teamworkers.domain.model.valueobjects;

import java.util.UUID;

public record WorkerId(String value) {
    public WorkerId() {
        this("wrk-" + UUID.randomUUID().toString().replace("-", ""));
    }

    public WorkerId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("WorkerId cannot be null or empty");
        }
        this.value = value;
    }
}