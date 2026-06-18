package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

public interface SupplierOfferQueryService {
    Optional<SupplierOffer> handle(Long id);
    List<SupplierOffer> handleGetAll();
    List<SupplierOffer> handleGetBySupplierId(SupplierId supplierId);
    List<SupplierOffer> handleGetByMaterialCatalogId(MaterialCatalogId materialCatalogId);
}
