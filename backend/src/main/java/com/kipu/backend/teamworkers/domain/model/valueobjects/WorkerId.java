package com.kipu.backend.teamworkers.domain.model.valueobjects;

import com.kipu.backend.teamworkers.domain.model.exceptions.DomainValidationException;

import java.util.UUID;

public record WorkerId(String value) {
    public WorkerId() {
        this("wrk-" + UUID.randomUUID().toString().replace("-", ""));
    }

    public WorkerId(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("worker.validation.workerId");
        }
        this.value = value;
    }
}