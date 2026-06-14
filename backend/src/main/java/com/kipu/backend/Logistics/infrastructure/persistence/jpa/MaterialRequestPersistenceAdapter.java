package com.kipu.backend.Logistics.infrastructure.persistence.jpa;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialRequestRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.mappers.MaterialRequestPersistenceMapper;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories.SpringDataMaterialRequestJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaterialRequestPersistenceAdapter implements MaterialRequestRepository {

    private final SpringDataMaterialRequestJpaRepository repository;

    public MaterialRequestPersistenceAdapter(SpringDataMaterialRequestJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaterialRequest save(MaterialRequest materialRequest) {
        var entity = MaterialRequestPersistenceMapper.toJpaEntity(materialRequest);
        var savedEntity = repository.save(entity);
        return MaterialRequestPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MaterialRequest> findById(Long id) {
        return repository.findByIdWithItems(id).map(MaterialRequestPersistenceMapper::toDomain);
    }

    @Override
    public List<MaterialRequest> findByRequestStatus(RequestStatus requestStatus) {
        return repository.findByRequestStatus(requestStatus).stream()
                .map(MaterialRequestPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialRequest> findAll() {
        return repository.findAllWithItems().stream()
                .map(MaterialRequestPersistenceMapper::toDomain)
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