package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.MaterialCatalogIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.SupplierIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "material_request_item")
public class MaterialRequestItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_request_id", nullable = false)
    private MaterialRequestJpaEntity materialRequest;

    @Column(name = "material_catalog_id", nullable = false)
    @Convert(converter = MaterialCatalogIdAttributeConverter.class)
    private MaterialCatalogId materialCatalogId;

    @Column(name = "supplier_id", nullable = false)
    @Convert(converter = SupplierIdAttributeConverter.class)
    private SupplierId supplierId;

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public MaterialRequestItemJpaEntity(Long id, MaterialRequestJpaEntity materialRequest,
                                        MaterialCatalogId materialCatalogId, SupplierId supplierId,
                                        BigDecimal quantity, BigDecimal unitPrice) {
        this.id = id;
        this.materialRequest = materialRequest;
        this.materialCatalogId = materialCatalogId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}