package com.kipu.backend.teamworkers.application.internal.queriesservices;

import com.kipu.backend.teamworkers.application.queries.GetAllTeamWorkersByProjectIdQuery;
import com.kipu.backend.teamworkers.application.queries.GetTeamWorkerByIdQuery;
import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;

import java.util.List;
import java.util.Optional;

public interface TeamWorkerQueryService {
    Optional<TeamWorker> handle(GetTeamWorkerByIdQuery query);
    List<TeamWorker> handle(GetAllTeamWorkersByProjectIdQuery query);
}