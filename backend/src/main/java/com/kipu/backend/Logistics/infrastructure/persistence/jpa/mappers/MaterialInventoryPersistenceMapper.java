package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialInventoryJpaEntity;

public final class MaterialInventoryPersistenceMapper {

    private MaterialInventoryPersistenceMapper() {}

    public static MaterialInventoryJpaEntity toJpaEntity(MaterialInventory aggregate) {
        return new MaterialInventoryJpaEntity(
                aggregate.getId(),
                aggregate.getProjectId(),
                aggregate.getMaterialCatalogId(),
                aggregate.getCurrentStock(),
                aggregate.getMinimumStock(),
                aggregate.getLocation(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static MaterialInventory toDomain(MaterialInventoryJpaEntity entity) {
        return MaterialInventory.rehydrate(
                entity.getId(),
                entity.getProjectId(),
                entity.getMaterialCatalogId(),
                entity.getCurrentStock(),
                entity.getMinimumStock(),
                entity.getLocation(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}