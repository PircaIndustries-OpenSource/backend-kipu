package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCatalogRepository;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialInventoryRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialInventoryPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialInventoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaterialInventoryPersistenceAdapter implements MaterialInventoryRepository {

    private final SpringDataMaterialInventoryJpaRepository repository;
    private final MaterialCatalogRepository catalogRepository;

    public MaterialInventoryPersistenceAdapter(SpringDataMaterialInventoryJpaRepository repository,
                                               MaterialCatalogRepository catalogRepository) {
        this.repository = repository;
        this.catalogRepository = catalogRepository;
    }

    @Override
    public MaterialInventory save(MaterialInventory materialInventory) {
        var entity = MaterialInventoryPersistenceMapper.toJpaEntity(materialInventory);
        var savedEntity = repository.save(entity);
        return MaterialInventoryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MaterialInventory> findById(Long id) {
        return repository.findById(id).map(MaterialInventoryPersistenceMapper::toDomain);
    }

    @Override
    public List<MaterialInventory> findByCategoryId(CategoryId categoryId) {
        List<MaterialCatalog> catalogs = catalogRepository.findByCategoryId(categoryId);
        List<MaterialCatalogId> catalogIds = catalogs.stream()
                .map(c -> new MaterialCatalogId(c.getId().intValue()))
                .collect(Collectors.toList());
        if (catalogIds.isEmpty()) {
            return List.of();
        }
        return repository.findByMaterialCatalogIdIn(catalogIds).stream()
                .map(MaterialInventoryPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialInventory> findAll() {
        return repository.findAll().stream()
                .map(MaterialInventoryPersistenceMapper::toDomain)
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