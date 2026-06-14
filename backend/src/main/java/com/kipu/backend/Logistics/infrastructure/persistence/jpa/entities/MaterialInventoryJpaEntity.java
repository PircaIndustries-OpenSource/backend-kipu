package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WarehouseLocation;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.MaterialCatalogIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.ProjectIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.QuantityAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.WarehouseLocationAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "material_inventory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"project_id", "material_catalog_id"},
                        name = "uk_material_inventory_project_material")
        })
public class MaterialInventoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    @Convert(converter = ProjectIdAttributeConverter.class)
    private ProjectId projectId;

    @Column(name = "material_catalog_id", nullable = false)
    @Convert(converter = MaterialCatalogIdAttributeConverter.class)
    private MaterialCatalogId materialCatalogId;

    @Column(name = "current_stock", nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity currentStock;

    @Column(name = "minimum_stock", nullable = false)
    @Convert(converter = QuantityAttributeConverter.class)
    private Quantity minimumStock;

    @Column(name = "location", nullable = false, length = 255)
    @Convert(converter = WarehouseLocationAttributeConverter.class)
    private WarehouseLocation location;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public MaterialInventoryJpaEntity(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                                      Quantity currentStock, Quantity minimumStock, WarehouseLocation location,
                                      Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.materialCatalogId = materialCatalogId;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}