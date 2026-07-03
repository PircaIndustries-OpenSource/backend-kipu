package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialWasteJpaEntity;

public final class MaterialWastePersistenceMapper {

    private MaterialWastePersistenceMapper() {}

    public static MaterialWasteJpaEntity toJpaEntity(MaterialWaste aggregate) {
        return new MaterialWasteJpaEntity(
                aggregate.getId(),
                aggregate.getProjectId(),
                aggregate.getMaterialCatalogId(),
                aggregate.getQuantity(),
                aggregate.getUnit(),
                aggregate.getClassificationType(),
                aggregate.getDate(),
                aggregate.getDescription(),
                aggregate.getReportedBy(),
                aggregate.getPhotoUrl(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static MaterialWaste toDomain(MaterialWasteJpaEntity entity) {
        return MaterialWaste.rehydrate(
                entity.getId(),
                entity.getProjectId(),
                entity.getMaterialCatalogId(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getClassificationType(),
                entity.getDate(),
                entity.getDescription(),
                entity.getReportedBy(),
                entity.getPhotoUrl(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
