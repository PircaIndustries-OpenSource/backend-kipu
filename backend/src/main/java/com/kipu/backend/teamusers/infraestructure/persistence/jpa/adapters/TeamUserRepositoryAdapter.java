package com.kipu.backend.teamusers.infraestructure.persistence.jpa.adapters;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.repositories.TeamUserRepository;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.mappers.TeamUserMapper;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.repositories.TeamUserJpaRepository;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.specifications.TeamUserSpecifications;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamUserRepositoryAdapter implements TeamUserRepository {

    private final TeamUserJpaRepository jpaRepository;
    private final TeamUserMapper mapper;

    public TeamUserRepositoryAdapter(TeamUserJpaRepository jpaRepository, TeamUserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TeamUser save(TeamUser teamUser) {
        var entity = mapper.toJpa(teamUser);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<TeamUser> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TeamUser> findByIsActiveTrue(String projectId) {
        return jpaRepository.findByIsActiveTrue(projectId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TeamUser> search(String projectId, String searchTerm) {

        var entities = jpaRepository.findAll(TeamUserSpecifications.searchByTerm(projectId, searchTerm));

        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TeamUser> findByProjectId(String projectId) {
        var entities = jpaRepository.findByProjectId(projectId);
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<TeamUser> findByEmailAddressAndProjectId(EmailAddress email, String projectId) {
        var entities = jpaRepository.findByEmailAndProjectId(email.address(), projectId);
        return entities.map(mapper::toDomain);
    }
}
