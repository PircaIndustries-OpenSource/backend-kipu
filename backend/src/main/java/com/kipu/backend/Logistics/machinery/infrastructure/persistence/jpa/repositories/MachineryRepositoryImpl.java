package com.kipu.backend.logistics.machinery.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.logistics.machinery.domain.model.entities.Machinery;
import com.kipu.backend.logistics.machinery.domain.repositories.MachineryRepository;
import com.kipu.backend.logistics.machinery.infrastructure.persistence.jpa.entities.MachineryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MachineryRepositoryImpl implements MachineryRepository {

    private final MachineryJpaRepository jpaRepository;

    public MachineryRepositoryImpl(MachineryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private Machinery toDomain(MachineryJpaEntity entity) {
        Machinery machinery = new Machinery(
                entity.getName(),
                entity.getStatus(),
                entity.getAssignedTo(),
                entity.getRegistrationDate(),
                entity.getMaintenanceHours(),
                entity.getAssignmentDetail(),
                entity.getProjectId()
        );
        machinery.setId(entity.getId());
        return machinery;
    }

    private MachineryJpaEntity toJpa(Machinery machinery) {
        MachineryJpaEntity entity = new MachineryJpaEntity();
        if (machinery.getId() != null) {
            entity.setId(machinery.getId());
        }
        entity.setName(machinery.getName());
        entity.setStatus(machinery.getStatus());
        entity.setAssignedTo(machinery.getAssignedTo());
        entity.setRegistrationDate(machinery.getRegistrationDate());
        entity.setMaintenanceHours(machinery.getMaintenanceHours());
        entity.setAssignmentDetail(machinery.getAssignmentDetail());
        entity.setProjectId(machinery.getProjectId());
        return entity;
    }

    @Override
    public Machinery save(Machinery machinery) {
        return toDomain(jpaRepository.save(toJpa(machinery)));
    }

    @Override
    public Optional<Machinery> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Machinery> findByProjectId(String projectId) {
        return jpaRepository.findByProjectId(projectId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }
}
