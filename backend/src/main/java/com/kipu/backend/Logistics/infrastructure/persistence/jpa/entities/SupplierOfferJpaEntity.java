package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.MaterialCatalogIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "supplier_offer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"supplier_id", "material_catalog_id"}, name = "uk_supplier_offer_supplier_material")
        })
public class SupplierOfferJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", nullable = false)
    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(name = "material_catalog_id", nullable = false)
    @Convert(converter = MaterialCatalogIdAttributeConverter.class)
    private MaterialCatalogId materialCatalogId;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public SupplierOfferJpaEntity(Long id, SupplierId supplierId, MaterialCatalogId materialCatalogId,
                                  BigDecimal unitPrice, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.supplierId = supplierId;
        this.materialCatalogId = materialCatalogId;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
