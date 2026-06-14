package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.SupplierJpaEntity;

public final class SupplierPersistenceMapper {

    private SupplierPersistenceMapper() {}

    public static SupplierJpaEntity toJpaEntity(Supplier aggregate) {
        return new SupplierJpaEntity(
                aggregate.getId(),
                aggregate.getRuc(),
                aggregate.getSocialReason(),
                aggregate.getContact(),
                aggregate.getPhone(),
                aggregate.getEmail(),
                aggregate.getPaymentTerms(),
                aggregate.getIsActive(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static Supplier toDomain(SupplierJpaEntity entity) {
        return Supplier.rehydrate(
                entity.getId(),
                entity.getRuc(),
                entity.getSocialReason(),
                entity.getContact(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getPaymentTerms(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}