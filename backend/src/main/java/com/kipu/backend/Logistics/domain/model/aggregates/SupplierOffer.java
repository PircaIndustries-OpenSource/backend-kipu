package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class SupplierOffer {

    private final Long id;
    private final SupplierId supplierId;
    private final MaterialCatalogId materialCatalogId;
    private final BigDecimal unitPrice;
    private final Instant createdAt;
    private final Instant updatedAt;

    private SupplierOffer(Long id, SupplierId supplierId, MaterialCatalogId materialCatalogId,
                          BigDecimal unitPrice, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.supplierId = Objects.requireNonNull(supplierId, "supplier.offer.error.supplierId.notBlank");
        this.materialCatalogId = Objects.requireNonNull(materialCatalogId, "supplier.offer.error.materialCatalogId.notBlank");
        this.unitPrice = Objects.requireNonNull(unitPrice, "supplier.offer.error.unitPrice.notBlank");
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("supplier.offer.error.unitPrice.invalidValue");
        }
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SupplierOffer create(SupplierId supplierId, MaterialCatalogId materialCatalogId, BigDecimal unitPrice) {
        Instant now = Instant.now();
        return new SupplierOffer(null, supplierId, materialCatalogId, unitPrice, now, now);
    }

    public static SupplierOffer rehydrate(Long id, SupplierId supplierId, MaterialCatalogId materialCatalogId,
                                          BigDecimal unitPrice, Instant createdAt, Instant updatedAt) {
        return new SupplierOffer(id, supplierId, materialCatalogId, unitPrice, createdAt, updatedAt);
    }

    public SupplierOffer update(BigDecimal unitPrice) {
        return new SupplierOffer(
                this.id, this.supplierId, this.materialCatalogId,
                unitPrice != null ? unitPrice : this.unitPrice,
                this.createdAt, Instant.now()
        );
    }

    public Long getId() { return id; }
    public SupplierId getSupplierId() { return supplierId; }
    public MaterialCatalogId getMaterialCatalogId() { return materialCatalogId; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
