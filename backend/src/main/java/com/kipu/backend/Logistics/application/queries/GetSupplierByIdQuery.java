package com.kipu.backend.Logistics.application.queries;

public record GetSupplierByIdQuery(Long id) {
    public GetSupplierByIdQuery {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id cannot be null or negative");
    }
}