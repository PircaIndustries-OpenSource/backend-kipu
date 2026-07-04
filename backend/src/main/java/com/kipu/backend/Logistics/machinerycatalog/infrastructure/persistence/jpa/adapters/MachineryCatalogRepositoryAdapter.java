package com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;
import com.kipu.backend.Logistics.machinerycatalog.domain.repositories.MachineryCatalogRepository;
import com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.mappers.MachineryCatalogMapper;
import com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.repositories.MachineryCatalogJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MachineryCatalogRepositoryAdapter implements MachineryCatalogRepository {

    private final MachineryCatalogJpaRepository repository;

    public MachineryCatalogRepositoryAdapter(MachineryCatalogJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MachineryCatalog save(MachineryCatalog catalog) {
        var entity = MachineryCatalogMapper.toJpaEntity(catalog);
        var saved = repository.save(entity);
        return MachineryCatalogMapper.toDomain(saved);
    }

    @Override
    public Optional<MachineryCatalog> findById(String id) {
        return repository.findById(id).map(MachineryCatalogMapper::toDomain);
    }

    @Override
    public List<MachineryCatalog> findAll() {
        return repository.findAll().stream()
                .map(MachineryCatalogMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
