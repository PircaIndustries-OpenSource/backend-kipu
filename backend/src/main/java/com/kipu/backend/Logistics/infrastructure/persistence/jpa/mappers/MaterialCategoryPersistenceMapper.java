package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialCategoryJpaEntity;

public final class MaterialCategoryPersistenceMapper {

    private MaterialCategoryPersistenceMapper() {}

    public static MaterialCategoryJpaEntity toJpaEntity(MaterialCategory aggregate) {
        return new MaterialCategoryJpaEntity(
                aggregate.getId(),
                aggregate.getName(),
                aggregate.getDescription(),
                aggregate.getIsActive(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static MaterialCategory toDomain(MaterialCategoryJpaEntity entity) {
        return MaterialCategory.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}