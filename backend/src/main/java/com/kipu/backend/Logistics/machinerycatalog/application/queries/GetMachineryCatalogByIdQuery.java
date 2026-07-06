package com.kipu.backend.Logistics.machinerycatalog.application.queries;

public record GetMachineryCatalogByIdQuery(String id) {
    public GetMachineryCatalogByIdQuery {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("machinerycatalog.error.id.notBlank");
        }
    }
}
