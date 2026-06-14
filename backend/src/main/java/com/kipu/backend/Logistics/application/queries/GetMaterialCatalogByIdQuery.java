package com.kipu.backend.Logistics.application.queries;

public record GetMaterialCatalogByIdQuery(Long id) {
    public GetMaterialCatalogByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id cannot be null or negative");
    }
}