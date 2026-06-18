package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityFromSupplierOfferQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromOffer(SupplierOffer offer) {
        return ResponseEntity.ok(SupplierOfferResourceFromEntityAssembler.toResourceFromEntity(offer));
    }

    public static ResponseEntity<?> toResponseEntityFromList(List<SupplierOffer> offers) {
        return ResponseEntity.ok(SupplierOfferResourceFromEntityAssembler.toResourceListFromEntityList(offers));
    }
}
