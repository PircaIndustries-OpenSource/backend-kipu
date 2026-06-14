package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.SupplierPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataSupplierJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupplierPersistenceAdapter implements SupplierRepository {

    private final SpringDataSupplierJpaRepository repository;

    public SupplierPersistenceAdapter(SpringDataSupplierJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Supplier save(Supplier supplier) {
        var entity = SupplierPersistenceMapper.toJpaEntity(supplier);
        var savedEntity = repository.save(entity);
        return SupplierPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return repository.findById(id).map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Supplier> findByRuc(Ruc ruc) {
        return repository.findByRuc(ruc).map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public List<Supplier> findByIsActive(Boolean isActive) {
        return repository.findByIsActive(isActive).stream()
                .map(SupplierPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Supplier> findAll() {
        return repository.findAll().stream()
                .map(SupplierPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByRuc(Ruc ruc) {
        return repository.existsByRuc(ruc);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}