package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialRequestResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialRequestItemResource;

import java.util.stream.Collectors;

public class MaterialRequestResourceFromEntityAssembler {

    public static MaterialRequestResource toResourceFromEntity(MaterialRequest entity) {
        var itemResources = entity.getItems().stream()
                .map(item -> new MaterialRequestItemResource(
                        item.getId(),
                        item.getMaterialCatalogId().value(),
                        item.getSupplierId().value(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        return new MaterialRequestResource(
                entity.getId(),
                entity.getDeadline(),
                entity.getRequestStatus().name(),
                entity.getRequestPriority().name(),
                entity.getDeliveryLocation(),
                entity.getBudgetLineId().value(),
                entity.getPurpose(),
                entity.getAdditionalNotes(),
                entity.getRequestedBy().value(),
                itemResources
        );
    }
}