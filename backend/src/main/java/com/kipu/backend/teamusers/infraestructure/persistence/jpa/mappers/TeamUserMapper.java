package com.kipu.backend.teamusers.infraestructure.persistence.jpa.mappers;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import com.kipu.backend.teamusers.infraestructure.persistence.jpa.entities.TeamUserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TeamUserMapper {

    public TeamUser toDomain(TeamUserJpaEntity entity) {
        var fullName = new FullName(entity.getFullName());
        var email = new EmailAddress(entity.getEmail());

        return new TeamUser(
                entity.getId(),
                entity.getUserId(),
                fullName,
                email,
                entity.getRole(),
                entity.isActive(),
                entity.getProjectId()
        );
    }

    public TeamUserJpaEntity toJpa(TeamUser teamUser) {
        var entity = new TeamUserJpaEntity(
                teamUser.getId(),
                teamUser.getUserId(),
                teamUser.getFullName().fullName(),
                teamUser.getEmail().address(),
                teamUser.getRole(),
                teamUser.getProjectId()
        );
        entity.setActive(teamUser.isActive());
        return entity;
    }
}
