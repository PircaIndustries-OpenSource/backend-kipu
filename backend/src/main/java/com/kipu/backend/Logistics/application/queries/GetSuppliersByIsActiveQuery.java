package com.kipu.backend.Logistics.application.queries;

public record GetSuppliersByIsActiveQuery(Boolean isActive) {
    public GetSuppliersByIsActiveQuery {
        if (isActive == null)
            throw new IllegalArgumentException("isActive cannot be null");
    }
}