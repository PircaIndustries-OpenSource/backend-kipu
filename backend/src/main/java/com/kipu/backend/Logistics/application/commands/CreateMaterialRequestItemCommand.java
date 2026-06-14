package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.math.BigDecimal;

/**
 * Command for creating a material request item.
 *
 * @param materialCatalogId the material catalog ID
 * @param supplierId        the supplier ID
 * @param quantity          the requested quantity
 * @param unitPrice         the unit price
 */
public record CreateMaterialRequestItemCommand(MaterialCatalogId materialCatalogId, SupplierId supplierId,
                                               BigDecimal quantity, BigDecimal unitPrice) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if any parameter is null,
     *                                  or if quantity/unitPrice are <= 0
     */
    public CreateMaterialRequestItemCommand {
        if (materialCatalogId == null)
            throw new IllegalArgumentException("materialCatalogId cannot be null");
        if (supplierId == null)
            throw new IllegalArgumentException("supplierId cannot be null");
        if (quantity == null)
            throw new IllegalArgumentException("quantity cannot be null");
        if (unitPrice == null)
            throw new IllegalArgumentException("unitPrice cannot be null");
        if (quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("quantity must be positive");
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("unitPrice must be positive");
    }
}