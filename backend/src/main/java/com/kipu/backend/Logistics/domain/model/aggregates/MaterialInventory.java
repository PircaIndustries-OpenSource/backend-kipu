package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

import java.time.Instant;
import java.util.Objects;

public class MaterialInventory {

    private final Long id;
    private final ProjectId projectId;
    private final MaterialCatalogId materialCatalogId;
    private final Quantity currentStock;
    private final Quantity minimumStock;
    private final WarehouseLocation location;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialInventory(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                              Quantity currentStock, Quantity minimumStock, WarehouseLocation location,
                              Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "material.inventory.error.projectId.notBlank");
        this.materialCatalogId = Objects.requireNonNull(materialCatalogId, "material.inventory.error.materialCatalogId.notBlank");
        this.currentStock = Objects.requireNonNull(currentStock, "material.inventory.error.currentStock.notBlank");
        this.minimumStock = minimumStock != null ? minimumStock : new Quantity(0);
        this.location = Objects.requireNonNull(location, "material.inventory.error.location.notBlank");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MaterialInventory create(ProjectId projectId, MaterialCatalogId materialCatalogId,
                                           Quantity currentStock, Quantity minimumStock, WarehouseLocation location) {
        Instant now = Instant.now();
        return new MaterialInventory(null, projectId, materialCatalogId, currentStock, minimumStock, location, now, now);
    }

    public static MaterialInventory rehydrate(Long id, ProjectId projectId, MaterialCatalogId materialCatalogId,
                                              Quantity currentStock, Quantity minimumStock, WarehouseLocation location,
                                              Instant createdAt, Instant updatedAt) {
        return new MaterialInventory(id, projectId, materialCatalogId, currentStock, minimumStock, location, createdAt, updatedAt);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public MaterialCatalogId getMaterialCatalogId() {
        return materialCatalogId;
    }

    public Quantity getCurrentStock() {
        return currentStock;
    }

    public Quantity getMinimumStock() {
        return minimumStock;
    }

    public WarehouseLocation getLocation() {
        return location;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}