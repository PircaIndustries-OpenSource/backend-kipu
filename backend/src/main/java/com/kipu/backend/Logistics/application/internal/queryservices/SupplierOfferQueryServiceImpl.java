package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queryservices.SupplierOfferQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierOfferRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierOfferQueryServiceImpl implements SupplierOfferQueryService {

    private final SupplierOfferRepository repository;

    public SupplierOfferQueryServiceImpl(SupplierOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<SupplierOffer> handle(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SupplierOffer> handleGetAll() {
        return repository.findAll();
    }

    @Override
    public List<SupplierOffer> handleGetBySupplierId(SupplierId supplierId) {
        return repository.findBySupplierId(supplierId);
    }

    @Override
    public List<SupplierOffer> handleGetByMaterialCatalogId(MaterialCatalogId materialCatalogId) {
        return repository.findByMaterialCatalogId(materialCatalogId);
    }
}
