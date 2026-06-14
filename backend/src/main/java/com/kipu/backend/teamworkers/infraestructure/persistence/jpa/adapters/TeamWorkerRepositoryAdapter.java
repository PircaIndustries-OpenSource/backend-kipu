package com.kipu.backend.teamworkers.infraestructure.persistence.jpa.adapters;

import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.domain.model.repositories.TeamWorkerRepository;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;
import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities.TeamWorkerJpaEntity;
import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.mappers.TeamWorkerMapper;
import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.repositories.TeamWorkerJpaRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamWorkerRepositoryAdapter implements TeamWorkerRepository {

    private final TeamWorkerJpaRepository jpaRepository;
    private final TeamWorkerMapper mapper;

    public TeamWorkerRepositoryAdapter(TeamWorkerJpaRepository jpaRepository, TeamWorkerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TeamWorker save(TeamWorker teamWorker) {
        var entity = mapper.toJpa(teamWorker);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void delete(TeamWorker teamWorker) {
        jpaRepository.deleteById(teamWorker.getId().value());
    }

    @Override
    public Optional<TeamWorker> findById(WorkerId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TeamWorker> findByProjectId(String projectId, String globalSearch) {
        // Implementación de Especificación (equivalente al query.Where(...) en C# EF Core)
        Specification<TeamWorkerJpaEntity> spec = (root, query, builder) -> {
            var projectPredicate = builder.equal(root.get("projectId"), projectId);

            if (globalSearch == null || globalSearch.isBlank()) {
                return projectPredicate;
            }

            String pattern = "%" + globalSearch.toLowerCase() + "%";
            var searchPredicate = builder.or(
                    builder.like(builder.lower(root.get("dni")), pattern),
                    builder.like(builder.lower(root.get("fullName")), pattern),
                    builder.like(builder.lower(root.get("role")), pattern)
            );

            return builder.and(projectPredicate, searchPredicate);
        };

        var entities = jpaRepository.findAll(spec);
        return entities.stream().map(mapper::toDomain).toList();
    }
}