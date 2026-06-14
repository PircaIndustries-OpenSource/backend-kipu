package com.kipu.backend.Logistics.application.queries;
public record GetMaterialInventoryByIdQuery(Long id) {
    public GetMaterialInventoryByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id cannot be null or negative");
    }
}
