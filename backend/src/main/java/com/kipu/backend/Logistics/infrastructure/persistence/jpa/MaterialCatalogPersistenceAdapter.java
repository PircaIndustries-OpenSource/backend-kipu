package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCatalogRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialCatalogPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialCatalogJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaterialCatalogPersistenceAdapter implements MaterialCatalogRepository {

    private final SpringDataMaterialCatalogJpaRepository repository;

    public MaterialCatalogPersistenceAdapter(SpringDataMaterialCatalogJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaterialCatalog save(MaterialCatalog materialCatalog) {
        var entity = MaterialCatalogPersistenceMapper.toJpaEntity(materialCatalog);
        var savedEntity = repository.save(entity);
        return MaterialCatalogPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MaterialCatalog> findById(Long id) {
        return repository.findById(id).map(MaterialCatalogPersistenceMapper::toDomain);
    }

    @Override
    public List<MaterialCatalog> findAll() {
        return repository.findAll().stream()
                .map(MaterialCatalogPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialCatalog> findByCategoryId(CategoryId categoryId) {
        return repository.findByCategoryId(categoryId).stream()
                .map(MaterialCatalogPersistenceMapper::toDomain)
                .collect(Collectors.toList());
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