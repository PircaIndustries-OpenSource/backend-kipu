package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.entities.MachineryJpaEntity;

public final class MachineryPersistenceMapper {

    private MachineryPersistenceMapper() {}

    public static MachineryJpaEntity toJpaEntity(Machinery aggregate) {
        return new MachineryJpaEntity(
                aggregate.getId(),
                aggregate.getName(),
                aggregate.getStatus(),
                aggregate.getAssignedTo(),
                aggregate.getRegistrationDate(),
                aggregate.getMaintenanceHours(),
                aggregate.getAssignmentDetail(),
                aggregate.getProjectId(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static Machinery toDomain(MachineryJpaEntity entity) {
        return Machinery.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getAssignedTo(),
                entity.getRegistrationDate(),
                entity.getMaintenanceHours(),
                entity.getAssignmentDetail(),
                entity.getProjectId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
