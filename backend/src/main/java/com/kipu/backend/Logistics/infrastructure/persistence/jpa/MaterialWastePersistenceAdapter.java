package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialWasteRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialWastePersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialWasteJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        try {
            return repository.findById(id).map(MaterialWastePersistenceMapper::toDomain);
        } catch (Exception e) {
            log.warn("Could not find waste by id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<MaterialWaste> findAll() {
        try {
            return repository.findAll().stream()
                    .map(MaterialWastePersistenceMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Could not query all wastes: {}", e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<MaterialWaste> findByProjectId(ProjectId projectId) {
        try {
            return repository.findByProjectId(projectId.value()).stream()
                    .map(MaterialWastePersistenceMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Could not query wastes by projectId (DB schema may need reset): {}", e.getMessage());
            return List.of();
        }
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
