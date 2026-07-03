package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.domain.repositories.MachineryRepository;
import com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.mappers.MachineryPersistenceMapper;
import com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.repositories.SpringDataMachineryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MachineryPersistenceAdapter implements MachineryRepository {

    private final SpringDataMachineryJpaRepository repository;

    public MachineryPersistenceAdapter(SpringDataMachineryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Machinery save(Machinery machinery) {
        var entity = MachineryPersistenceMapper.toJpaEntity(machinery);
        var savedEntity = repository.save(entity);
        return MachineryPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Machinery> findById(String id) {
        return repository.findById(id).map(MachineryPersistenceMapper::toDomain);
    }

    @Override
    public List<Machinery> findByProjectId(String projectId) {
        return repository.findByProjectId(projectId).stream()
                .map(MachineryPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Machinery> findAll() {
        return repository.findAll().stream()
                .map(MachineryPersistenceMapper::toDomain)
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
