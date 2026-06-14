package com.kipu.backend.Logistics.application.queries;

import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;

public record GetSupplierByRucQuery(Ruc ruc) {
    public GetSupplierByRucQuery {
        if (ruc == null)
            throw new IllegalArgumentException("ruc cannot be null");
    }
}

