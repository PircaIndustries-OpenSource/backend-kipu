package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.SupplierOfferJpaEntity;

public final class SupplierOfferPersistenceMapper {

    private SupplierOfferPersistenceMapper() {}

    public static SupplierOfferJpaEntity toJpaEntity(SupplierOffer aggregate) {
        return new SupplierOfferJpaEntity(
                aggregate.getId(),
                aggregate.getSupplierId(),
                aggregate.getMaterialCatalogId(),
                aggregate.getUnitPrice(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static SupplierOffer toDomain(SupplierOfferJpaEntity entity) {
        return SupplierOffer.rehydrate(
                entity.getId(),
                entity.getSupplierId(),
                entity.getMaterialCatalogId(),
                entity.getUnitPrice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
