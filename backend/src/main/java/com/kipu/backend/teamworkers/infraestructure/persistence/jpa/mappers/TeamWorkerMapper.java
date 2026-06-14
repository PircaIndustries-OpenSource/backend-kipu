package com.kipu.backend.teamworkers.infraestructure.persistence.jpa.mappers;

import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;
import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities.TeamWorkerJpaEntity;
import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities.WorkerMachineryJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamWorkerMapper {

    public TeamWorkerJpaEntity toJpa(TeamWorker domainEntity) {
        var entity = new TeamWorkerJpaEntity();
        entity.setId(domainEntity.getId().value());
        entity.setDni(domainEntity.getDni());
        entity.setFullName(domainEntity.getFullName());
        entity.setRole(domainEntity.getRole());
        entity.setActive(domainEntity.isActive());
        entity.setProjectId(domainEntity.getProjectId());

        List<WorkerMachineryJpaEntity> machineries = domainEntity.getMachineries().stream().map(m -> {
            var machineryEntity = new WorkerMachineryJpaEntity();
            machineryEntity.setMachineryId(m.getMachineryId());
            machineryEntity.setFullName(m.getFullName());
            return machineryEntity;
        }).toList();

        entity.getMachineries().addAll(machineries);
        return entity;
    }

    public TeamWorker toDomain(TeamWorkerJpaEntity jpaEntity) {

        TeamWorker worker = new TeamWorker(
                new WorkerId(jpaEntity.getId()),
                jpaEntity.getDni(),
                jpaEntity.getFullName(),
                jpaEntity.getRole(),
                jpaEntity.isActive(),
                jpaEntity.getProjectId()
        );

        jpaEntity.getMachineries().forEach(m ->
                worker.assignMachinery(m.getMachineryId(), m.getFullName())
        );

        return worker;
    }
}
