package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequestItem;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialRequestItemJpaEntity;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialRequestJpaEntity;

public final class MaterialRequestItemPersistenceMapper {

    private MaterialRequestItemPersistenceMapper() {}

    public static MaterialRequestItemJpaEntity toJpaEntity(MaterialRequestItem aggregate, MaterialRequestJpaEntity parent) {
        return new MaterialRequestItemJpaEntity(
                aggregate.getId(),
                parent,
                aggregate.getMaterialCatalogId(),
                aggregate.getSupplierId(),
                aggregate.getQuantity(),
                aggregate.getUnitPrice()
        );
    }

    public static MaterialRequestItem toDomain(MaterialRequestItemJpaEntity entity) {
        return MaterialRequestItem.rehydrate(
                entity.getId(),
                entity.getMaterialRequest().getId(),
                entity.getMaterialCatalogId(),
                entity.getSupplierId(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }
}