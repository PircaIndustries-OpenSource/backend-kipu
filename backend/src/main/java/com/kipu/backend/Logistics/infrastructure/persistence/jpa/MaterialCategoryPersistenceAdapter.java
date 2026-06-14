package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCategoryRepository;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialCategoryPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialCategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaterialCategoryPersistenceAdapter implements MaterialCategoryRepository {

    private final SpringDataMaterialCategoryJpaRepository repository;

    public MaterialCategoryPersistenceAdapter(SpringDataMaterialCategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaterialCategory save(MaterialCategory materialCategory) {
        var entity = MaterialCategoryPersistenceMapper.toJpaEntity(materialCategory);
        var savedEntity = repository.save(entity);
        return MaterialCategoryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MaterialCategory> findById(Long id) {
        return repository.findById(id).map(MaterialCategoryPersistenceMapper::toDomain);
    }

    @Override
    public List<MaterialCategory> findAll() {
        return repository.findAll().stream()
                .map(MaterialCategoryPersistenceMapper::toDomain)
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