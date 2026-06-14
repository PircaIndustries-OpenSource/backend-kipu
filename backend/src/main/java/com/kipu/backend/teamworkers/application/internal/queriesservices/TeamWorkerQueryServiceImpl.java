package com.kipu.backend.teamworkers.application.internal.queriesservices;

import com.kipu.backend.teamworkers.application.queries.GetAllTeamWorkersByProjectIdQuery;
import com.kipu.backend.teamworkers.application.queries.GetTeamWorkerByIdQuery;
import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.domain.model.repositories.TeamWorkerRepository;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamWorkerQueryServiceImpl implements TeamWorkerQueryService {

    private final TeamWorkerRepository teamWorkerRepository;

    public TeamWorkerQueryServiceImpl(TeamWorkerRepository teamWorkerRepository) {
        this.teamWorkerRepository = teamWorkerRepository;
    }

    @Override
    public Optional<TeamWorker> handle(GetTeamWorkerByIdQuery query) {
        return teamWorkerRepository.findById(new WorkerId(query.teamWorkerId()));
    }

    @Override
    public List<TeamWorker> handle(GetAllTeamWorkersByProjectIdQuery query) {
        return teamWorkerRepository.findByProjectId(query.projectId(), query.globalSearch());
    }
}