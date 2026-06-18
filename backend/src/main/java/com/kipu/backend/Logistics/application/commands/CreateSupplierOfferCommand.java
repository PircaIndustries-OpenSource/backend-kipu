package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.math.BigDecimal;

public record CreateSupplierOfferCommand(SupplierId supplierId, MaterialCatalogId materialCatalogId, BigDecimal unitPrice) {
    public CreateSupplierOfferCommand {
        if (supplierId == null) throw new IllegalArgumentException("supplierId cannot be null");
        if (materialCatalogId == null) throw new IllegalArgumentException("materialCatalogId cannot be null");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("unitPrice must be positive");
    }
}
