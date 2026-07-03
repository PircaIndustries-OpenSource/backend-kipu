package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialWasteRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialWastePersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialWasteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaterialWastePersistenceAdapter implements MaterialWasteRepository {

    private final SpringDataMaterialWasteJpaRepository repository;

    public MaterialWastePersistenceAdapter(SpringDataMaterialWasteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaterialWaste save(MaterialWaste materialWaste) {
        var entity = MaterialWastePersistenceMapper.toJpaEntity(materialWaste);
        var savedEntity = repository.save(entity);
        return MaterialWastePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MaterialWaste> findById(Long id) {
        return repository.findById(id).map(MaterialWastePersistenceMapper::toDomain);
    }

    @Override
    public List<MaterialWaste> findAll() {
        return repository.findAll().stream()
                .map(MaterialWastePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialWaste> findByProjectId(ProjectId projectId) {
        return repository.findByProjectId(projectId).stream()
                .map(MaterialWastePersistenceMapper::toDomain)
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
