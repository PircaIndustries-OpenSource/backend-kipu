package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.interfaces.rest.resources.SupplierOfferResource;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierOfferResourceFromEntityAssembler {

    public static SupplierOfferResource toResourceFromEntity(SupplierOffer entity) {
        return new SupplierOfferResource(
                entity.getId(),
                entity.getSupplierId().value(),
                entity.getMaterialCatalogId().value(),
                entity.getUnitPrice()
        );
    }

    public static List<SupplierOfferResource> toResourceListFromEntityList(List<SupplierOffer> entities) {
        return entities.stream()
                .map(SupplierOfferResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
