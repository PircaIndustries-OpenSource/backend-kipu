package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.math.BigDecimal;
import java.util.Objects;

public class MaterialRequestItem {

    private final Long id;
    private final Long materialRequestId;
    private final MaterialCatalogId materialCatalogId;
    private final SupplierId supplierId;
    private final BigDecimal quantity;
    private final BigDecimal unitPrice;

    private MaterialRequestItem(Long id, Long materialRequestId, MaterialCatalogId materialCatalogId,
                                SupplierId supplierId, BigDecimal quantity, BigDecimal unitPrice) {
        this.id = id;
        this.materialRequestId = materialRequestId;
        this.materialCatalogId = Objects.requireNonNull(materialCatalogId, "material.request.item.error.materialCatalogId.notBlank");
        this.supplierId = Objects.requireNonNull(supplierId, "material.request.item.error.supplierId.notBlank");
        this.quantity = Objects.requireNonNull(quantity, "material.request.item.error.quantity.notBlank");
        this.unitPrice = Objects.requireNonNull(unitPrice, "material.request.item.error.unitPrice.notBlank");
    }

    public static MaterialRequestItem create(MaterialCatalogId materialCatalogId,
                                             SupplierId supplierId, BigDecimal quantity, BigDecimal unitPrice) {
        return new MaterialRequestItem(null, null, materialCatalogId, supplierId, quantity, unitPrice);
    }

    public static MaterialRequestItem createWithRequestId(Long materialRequestId, MaterialCatalogId materialCatalogId,
                                                          SupplierId supplierId, BigDecimal quantity, BigDecimal unitPrice) {
        return new MaterialRequestItem(null, materialRequestId, materialCatalogId, supplierId, quantity, unitPrice);
    }

    public static MaterialRequestItem rehydrate(Long id, Long materialRequestId, MaterialCatalogId materialCatalogId,
                                                SupplierId supplierId, BigDecimal quantity, BigDecimal unitPrice) {
        return new MaterialRequestItem(id, materialRequestId, materialCatalogId, supplierId, quantity, unitPrice);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getMaterialRequestId() {
        return materialRequestId;
    }

    public MaterialCatalogId getMaterialCatalogId() {
        return materialCatalogId;
    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return quantity.multiply(unitPrice);
    }
}