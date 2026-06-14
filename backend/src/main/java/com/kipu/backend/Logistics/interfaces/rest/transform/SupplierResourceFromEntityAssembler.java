package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.interfaces.rest.resources.SupplierResource;

public class SupplierResourceFromEntityAssembler {

    public static SupplierResource toResourceFromEntity(Supplier entity) {
        return new SupplierResource(
                entity.getId(),
                entity.getRuc().value(),
                entity.getSocialReason().value(),
                entity.getContact(),
                entity.getPhone().value(),
                entity.getEmail().value(),
                entity.getPaymentTerms(),
                entity.getIsActive()
        );
    }
}