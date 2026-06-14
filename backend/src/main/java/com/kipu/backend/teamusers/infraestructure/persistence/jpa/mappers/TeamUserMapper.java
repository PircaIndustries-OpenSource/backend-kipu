package com.kipu.backend.teamusers.infraestructure.persistence.jpa.mappers;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.entities.TeamUserJpaEntity;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.repositories.TeamUserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TeamUserMapper {

    private final TeamUserJpaRepository teamUserJpaRepository;

    public TeamUserMapper(TeamUserJpaRepository teamUserJpaRepository) {
        this.teamUserJpaRepository = teamUserJpaRepository;
    }

    public TeamUser toDomain(TeamUserJpaEntity entity) {

        var fullName = new FullName(entity.getFullName());
        var email = new EmailAddress(entity.getEmail());

        return new TeamUser(
                entity.getId(),
                fullName,
                email,
                entity.getRole(),
                entity.isActive(),
                entity.getProjectId()
        );



    }

    public TeamUserJpaEntity toJpa(TeamUser teamUser) {
        var entity = new TeamUserJpaEntity(
                teamUser.getFullName().fullName(),
                teamUser.getEmail().address(),
                teamUser.getRole(),
                teamUser.getProjectId()
        );

        entity.setActive(teamUser.isActive());

        if (teamUser.getId() != null) {
            entity.setId(teamUser.getId());
        }
        return entity;
    }
}
