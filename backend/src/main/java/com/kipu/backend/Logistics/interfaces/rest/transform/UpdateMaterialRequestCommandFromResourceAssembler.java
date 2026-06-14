package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialRequestItemResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialRequestResource;

import java.util.stream.Collectors;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class UpdateMaterialRequestCommandFromResourceAssembler {

    public static UpdateMaterialRequestCommand toCommandFromResource(UpdateMaterialRequestResource resource) {
        var items = resource.items().stream()
                .map(UpdateMaterialRequestCommandFromResourceAssembler::toItemCommand)
                .collect(Collectors.toList());

        return new UpdateMaterialRequestCommand(
                resource.deadline(),
                toRequestPriorityFromString(resource.requestPriority()),
                resource.deliveryLocation(),
                resource.purpose(),
                resource.additionalNotes(),
                items
        );
    }

    public static UpdateMaterialRequestCommand toPatchCommandFromResource(UpdateMaterialRequestResource resource) {
        var items = resource.items() != null
                ? resource.items().stream()
                    .map(UpdateMaterialRequestCommandFromResourceAssembler::toItemCommand)
                    .collect(Collectors.toList())
                : null;

        return new UpdateMaterialRequestCommand(
                resource.deadline(),
                resource.requestPriority() != null ? toRequestPriorityFromString(resource.requestPriority()) : null,
                resource.deliveryLocation(),
                resource.purpose(),
                resource.additionalNotes(),
                items
        );
    }

    private static UpdateMaterialRequestItemCommand toItemCommand(UpdateMaterialRequestItemResource itemResource) {
        return new UpdateMaterialRequestItemCommand(
                toMaterialCatalogIdFromInteger(itemResource.materialCatalogId()),
                toSupplierIdFromInteger(itemResource.supplierId()),
                itemResource.quantity(),
                itemResource.unitPrice()
        );
    }
}
