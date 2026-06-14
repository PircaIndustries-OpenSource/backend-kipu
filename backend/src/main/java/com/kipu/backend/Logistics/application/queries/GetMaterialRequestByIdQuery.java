package com.kipu.backend.Logistics.application.queries;

public record GetMaterialRequestByIdQuery(Long id) {
    public GetMaterialRequestByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id cannot be null or negative");
    }
}