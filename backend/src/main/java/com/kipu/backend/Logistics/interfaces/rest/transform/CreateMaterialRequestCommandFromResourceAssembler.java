package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialRequestItemResource;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialRequestResource;

import java.util.stream.Collectors;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class CreateMaterialRequestCommandFromResourceAssembler {

    public static CreateMaterialRequestCommand toCommandFromResource(CreateMaterialRequestResource resource) {
        var items = resource.items().stream()
                .map(CreateMaterialRequestCommandFromResourceAssembler::toItemCommand)
                .collect(Collectors.toList());

        return new CreateMaterialRequestCommand(
                resource.deadline(),
                toRequestPriorityFromString(resource.requestPriority()),
                resource.deliveryLocation(),
                toBudgetLineIdFromInteger(resource.budgetLineId()),
                resource.purpose(),
                resource.additionalNotes(),
                toUserIdFromInteger(resource.requestedBy()),
                items
        );
    }

    private static CreateMaterialRequestItemCommand toItemCommand(CreateMaterialRequestItemResource itemResource) {
        return new CreateMaterialRequestItemCommand(
                toMaterialCatalogIdFromInteger(itemResource.materialCatalogId()),
                toSupplierIdFromInteger(itemResource.supplierId()),
                itemResource.quantity(),
                itemResource.unitPrice()
        );
    }
}