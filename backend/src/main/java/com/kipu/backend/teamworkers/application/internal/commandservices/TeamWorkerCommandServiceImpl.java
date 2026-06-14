package com.kipu.backend.teamworkers.application.internal.commandservices;

import com.kipu.backend.teamworkers.application.commands.AssignMachineryToTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.CreateTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.DeleteTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.RemoveMachineryFromTeamWorkerCommand;
import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.domain.model.repositories.TeamWorkerRepository;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamWorkerCommandServiceImpl implements TeamWorkerCommandService {

    private final TeamWorkerRepository teamWorkerRepository;

    public TeamWorkerCommandServiceImpl(TeamWorkerRepository teamWorkerRepository) {
        this.teamWorkerRepository = teamWorkerRepository;
    }

    @Override
    @Transactional
    public Optional<TeamWorker> handle(CreateTeamWorkerCommand command) {
        var worker = new TeamWorker(
                command.dni(),
                command.fullName(),
                command.role(),
                command.projectId()
        );

        if (command.machineries() != null) {
            command.machineries().forEach(m ->
                    worker.assignMachinery(m.machineryId(), m.fullName())
            );
        }

        return Optional.of(teamWorkerRepository.save(worker));
    }

    @Override
    @Transactional
    public boolean handle(DeleteTeamWorkerCommand command) {
        var workerId = new WorkerId(command.teamWorkerId());
        var workerOpt = teamWorkerRepository.findById(workerId);

        if (workerOpt.isEmpty()) return false;

        teamWorkerRepository.delete(workerOpt.get());
        return true;
    }

    @Override
    @Transactional
    public Optional<TeamWorker> handle(AssignMachineryToTeamWorkerCommand command) {
        var workerId = new WorkerId(command.teamWorkerId());

        return teamWorkerRepository.findById(workerId).map(worker -> {
            worker.assignMachinery(command.machineryId(), command.fullName());
            return teamWorkerRepository.save(worker);
        });
    }

    @Override
    @Transactional
    public Optional<TeamWorker> handle(RemoveMachineryFromTeamWorkerCommand command) {
        var workerId = new WorkerId(command.teamWorkerId());

        return teamWorkerRepository.findById(workerId).map(worker -> {
            worker.removeMachinery(command.machineryId());
            return teamWorkerRepository.save(worker);
        });
    }
}