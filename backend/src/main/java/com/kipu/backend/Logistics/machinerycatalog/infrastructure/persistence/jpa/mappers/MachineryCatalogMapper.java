package com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.valueobjects.CatalogName;
import com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.entities.MachineryCatalogJpaEntity;

public final class MachineryCatalogMapper {

    private MachineryCatalogMapper() {}

    public static MachineryCatalogJpaEntity toJpaEntity(MachineryCatalog domain) {
        return new MachineryCatalogJpaEntity(
                domain.getId(),
                domain.getName().value(),
                domain.getBrand(),
                domain.getModel(),
                domain.getSerialNumber(),
                domain.getAcquisitionDate()
        );
    }

    public static MachineryCatalog toDomain(MachineryCatalogJpaEntity entity) {
        return MachineryCatalog.rehydrate(
                entity.getId(),
                new CatalogName(entity.getName()),
                entity.getBrand(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getAcquisitionDate()
        );
    }
}
