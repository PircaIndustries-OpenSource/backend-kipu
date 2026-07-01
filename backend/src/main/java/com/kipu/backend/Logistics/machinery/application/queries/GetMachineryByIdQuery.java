package com.kipu.backend.Logistics.machinery.application.queries;

public record GetMachineryByIdQuery(String id) {
    public GetMachineryByIdQuery {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("machinery.error.id.notBlank");
        }
    }
}
