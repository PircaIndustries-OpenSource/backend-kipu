package com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequestItem;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialRequestItemJpaEntity;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialRequestJpaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MaterialRequestPersistenceMapper {

    private MaterialRequestPersistenceMapper() {}

    public static MaterialRequestJpaEntity toJpaEntity(MaterialRequest aggregate) {
        MaterialRequestJpaEntity entity = new MaterialRequestJpaEntity(
                aggregate.getId(),
                aggregate.getDeadline(),
                aggregate.getRequestStatus(),
                aggregate.getRequestPriority(),
                aggregate.getDeliveryLocation(),
                aggregate.getBudgetLineId(),
                aggregate.getPurpose(),
                aggregate.getAdditionalNotes(),
                aggregate.getRequestedBy(),
                null,
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
        List<MaterialRequestItemJpaEntity> itemEntities = aggregate.getItems().stream()
                .map(item -> MaterialRequestItemPersistenceMapper.toJpaEntity(item, entity))
                .collect(Collectors.toList());
        entity.getItems().addAll(itemEntities);

        return entity;
    }

    public static MaterialRequest toDomain(MaterialRequestJpaEntity entity) {
        List<MaterialRequestItem> items = entity.getItems().stream()
                .map(MaterialRequestItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());

        return MaterialRequest.rehydrate(
                entity.getId(),
                entity.getDeadline(),
                entity.getRequestStatus(),
                entity.getRequestPriority(),
                entity.getDeliveryLocation(),
                entity.getBudgetLineId(),
                entity.getPurpose(),
                entity.getAdditionalNotes(),
                entity.getRequestedBy(),
                items,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}