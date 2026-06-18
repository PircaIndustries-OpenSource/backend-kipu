package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

public interface SupplierOfferRepository {
    SupplierOffer save(SupplierOffer supplierOffer);
    Optional<SupplierOffer> findById(Long id);
    List<SupplierOffer> findAll();
    List<SupplierOffer> findBySupplierId(SupplierId supplierId);
    List<SupplierOffer> findByMaterialCatalogId(MaterialCatalogId materialCatalogId);
    Optional<SupplierOffer> findBySupplierIdAndMaterialCatalogId(SupplierId supplierId, MaterialCatalogId materialCatalogId);
    boolean existsById(Long id);
    void deleteById(Long id);
}
