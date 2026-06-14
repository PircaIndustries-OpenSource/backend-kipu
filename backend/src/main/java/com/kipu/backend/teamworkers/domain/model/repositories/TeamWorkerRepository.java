package com.kipu.backend.teamworkers.domain.model.repositories;

import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;

import java.util.List;
import java.util.Optional;

public interface TeamWorkerRepository {
    TeamWorker save(TeamWorker teamWorker);
    void delete(TeamWorker teamWorker);
    Optional<TeamWorker> findById(WorkerId id);
    List<TeamWorker> findByProjectId(String projectId, String globalSearch);
}