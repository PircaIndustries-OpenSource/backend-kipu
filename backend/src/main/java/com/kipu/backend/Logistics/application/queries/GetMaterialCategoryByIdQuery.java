package com.kipu.backend.Logistics.application.queries;

public record GetMaterialCategoryByIdQuery(Long id) {
    public GetMaterialCategoryByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id cannot be null or negative");
    }
}