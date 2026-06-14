package com.kipu.backend.teamusers.application.internal.queriesservices;

import com.kipu.backend.teamusers.application.queries.*;
import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.repositories.TeamUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamUserQueryServiceImpl implements TeamUserQueryService {

    private final TeamUserRepository teamUserRepository;

    public TeamUserQueryServiceImpl(TeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }

    @Override
    public List<TeamUser> handle(GetAllTeamUsersQuery query) {
        return this.teamUserRepository.findByProjectId(query.projectId());
    }

    @Override
    public Optional<TeamUser> handle(GetUserByIdQuery query) {
        return this.teamUserRepository.findById(query.id());
    }

    @Override
    public List<TeamUser> handle(SearchTeamUsersQuery query) {
        return this.teamUserRepository.search(query.projectId(), query.searchTerm());
    }

    @Override
    public List<TeamUser> handle(GetAllActiveTeamUsersQuery query) {
        return this.teamUserRepository.findByIsActiveTrue(query.projectId());
    }


}
