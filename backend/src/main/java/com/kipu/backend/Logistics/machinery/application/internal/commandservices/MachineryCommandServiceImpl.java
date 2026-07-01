package com.kipu.backend.Logistics.machinery.application.internal.commandservices;

import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandFailure;
import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandService;
import com.kipu.backend.Logistics.machinery.application.commands.CreateMachineryCommand;
import com.kipu.backend.Logistics.machinery.application.commands.UpdateMachineryCommand;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.MachineryStatus;
import com.kipu.backend.Logistics.machinery.domain.repositories.MachineryRepository;
import com.kipu.backend.shared.application.result.Result;
import com.kipu.backend.teamworkers.application.commands.AssignMachineryToTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.RemoveMachineryFromTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.internal.commandservices.TeamWorkerCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MachineryCommandServiceImpl implements MachineryCommandService {

    private final MachineryRepository repository;
    private final TeamWorkerCommandService teamWorkerCommandService;

    public MachineryCommandServiceImpl(MachineryRepository repository,
                                       TeamWorkerCommandService teamWorkerCommandService) {
        this.repository = repository;
        this.teamWorkerCommandService = teamWorkerCommandService;
    }

    @Override
    @Transactional
    public Result<Machinery, MachineryCommandFailure> handle(CreateMachineryCommand command) {
        try {
            var machinery = Machinery.create(
                    command.name(),
                    command.assignedTo(),
                    command.assignmentDetail(),
                    command.projectId()
            );
            var saved = repository.save(machinery);
            log.info("Machinery created: id={}, name={}", saved.getId(), saved.getName().value());
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error creating machinery", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Result<Machinery, MachineryCommandFailure> handleUpdate(String id, UpdateMachineryCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Machinery not found for update: id={}", id);
            return Result.failure(new MachineryCommandFailure.NotFound());
        }
        try {
            var updated = existing.get().update(
                    command.name(),
                    command.status(),
                    command.assignedTo(),
                    command.assignedWorkerId(),
                    command.maintenanceHours(),
                    command.assignmentDetail()
            );
            var saved = repository.save(updated);
            log.info("Machinery updated: id={}", saved.getId());
            syncWorkerOnStatusChange(existing.get(), saved);
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error updating machinery id={}", id, e);
            return Result.failure(new MachineryCommandFailure.UpdateFailed());
        }
    }

    @Override
    @Transactional
    public Result<Machinery, MachineryCommandFailure> handlePatch(String id, UpdateMachineryCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Machinery not found for patch: id={}", id);
            return Result.failure(new MachineryCommandFailure.NotFound());
        }
        try {
            var updated = existing.get().update(
                    command.name(),
                    command.status(),
                    command.assignedTo(),
                    command.assignedWorkerId(),
                    command.maintenanceHours(),
                    command.assignmentDetail()
            );
            var saved = repository.save(updated);
            log.info("Machinery patched: id={}", saved.getId());
            syncWorkerOnStatusChange(existing.get(), saved);
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error patching machinery id={}", id, e);
            return Result.failure(new MachineryCommandFailure.UpdateFailed());
        }
    }

    private void syncWorkerOnStatusChange(Machinery before, Machinery after) {
        String machineryId = after.getId();
        String machineryName = after.getName().value();

        if (after.getStatus() == MachineryStatus.IN_USE && after.getAssignedWorkerId() != null) {
            var assignCmd = new AssignMachineryToTeamWorkerCommand(
                    after.getAssignedWorkerId(), machineryId, machineryName);
            teamWorkerCommandService.handle(assignCmd);
            log.debug("Assigned machinery {} to worker {}", machineryId, after.getAssignedWorkerId());
        }

        if (after.getStatus() != MachineryStatus.IN_USE && before.getAssignedWorkerId() != null) {
            var removeCmd = new RemoveMachineryFromTeamWorkerCommand(
                    before.getAssignedWorkerId(), machineryId);
            teamWorkerCommandService.handle(removeCmd);
            log.debug("Removed machinery {} from worker {}", machineryId, before.getAssignedWorkerId());
        }
    }

    @Override
    @Transactional
    public Result<Void, MachineryCommandFailure> handleDelete(String id) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Machinery not found for deletion: id={}", id);
            return Result.failure(new MachineryCommandFailure.NotFound());
        }
        try {
            repository.deleteById(id);
            log.info("Machinery deleted: id={}", id);
            return Result.success(null);
        } catch (Exception e) {
            log.error("Error deleting machinery id={}", id, e);
            return Result.failure(new MachineryCommandFailure.DeleteFailed());
        }
    }
}
