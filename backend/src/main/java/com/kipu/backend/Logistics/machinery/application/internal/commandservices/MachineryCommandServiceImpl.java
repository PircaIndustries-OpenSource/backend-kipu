package com.kipu.backend.Logistics.machinery.application.internal.commandservices;

import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandFailure;
import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandService;
import com.kipu.backend.Logistics.machinery.application.commands.CreateMachineryCommand;
import com.kipu.backend.Logistics.machinery.application.commands.UpdateMachineryCommand;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.domain.repositories.MachineryRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MachineryCommandServiceImpl implements MachineryCommandService {

    private final MachineryRepository repository;

    public MachineryCommandServiceImpl(MachineryRepository repository) {
        this.repository = repository;
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
                    command.maintenanceHours(),
                    command.assignmentDetail()
            );
            var saved = repository.save(updated);
            log.info("Machinery updated: id={}", saved.getId());
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
                    command.maintenanceHours(),
                    command.assignmentDetail()
            );
            var saved = repository.save(updated);
            log.info("Machinery patched: id={}", saved.getId());
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error patching machinery id={}", id, e);
            return Result.failure(new MachineryCommandFailure.UpdateFailed());
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
