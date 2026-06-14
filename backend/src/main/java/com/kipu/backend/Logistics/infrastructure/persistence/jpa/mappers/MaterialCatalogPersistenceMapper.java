package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialCatalogJpaEntity;

public final class MaterialCatalogPersistenceMapper {

    private MaterialCatalogPersistenceMapper() {}

    public static MaterialCatalogJpaEntity toJpaEntity(MaterialCatalog aggregate) {
        return new MaterialCatalogJpaEntity(
                aggregate.getId(),
                aggregate.getName(),
                aggregate.getCategoryId(),
                aggregate.getMeasureUnit(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static MaterialCatalog toDomain(MaterialCatalogJpaEntity entity) {
        return MaterialCatalog.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getCategoryId(),
                entity.getMeasureUnit(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}