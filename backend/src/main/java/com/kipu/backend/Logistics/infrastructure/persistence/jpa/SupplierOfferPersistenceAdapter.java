package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierOfferRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.SupplierOfferPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataSupplierOfferJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupplierOfferPersistenceAdapter implements SupplierOfferRepository {

    private final SpringDataSupplierOfferJpaRepository repository;

    public SupplierOfferPersistenceAdapter(SpringDataSupplierOfferJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplierOffer save(SupplierOffer supplierOffer) {
        var entity = SupplierOfferPersistenceMapper.toJpaEntity(supplierOffer);
        var savedEntity = repository.save(entity);
        return SupplierOfferPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SupplierOffer> findById(Long id) {
        return repository.findById(id).map(SupplierOfferPersistenceMapper::toDomain);
    }

    @Override
    public List<SupplierOffer> findAll() {
        return repository.findAll().stream()
                .map(SupplierOfferPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierOffer> findBySupplierId(SupplierId supplierId) {
        return repository.findBySupplierId(supplierId).stream()
                .map(SupplierOfferPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierOffer> findByMaterialCatalogId(MaterialCatalogId materialCatalogId) {
        return repository.findByMaterialCatalogId(materialCatalogId).stream()
                .map(SupplierOfferPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SupplierOffer> findBySupplierIdAndMaterialCatalogId(SupplierId supplierId, MaterialCatalogId materialCatalogId) {
        return repository.findBySupplierIdAndMaterialCatalogId(supplierId, materialCatalogId)
                .map(SupplierOfferPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
