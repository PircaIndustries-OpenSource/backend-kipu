package com.kipu.backend.Logistics.machinery.application.commandservices;

import com.kipu.backend.Logistics.machinery.application.commands.CreateMachineryCommand;
import com.kipu.backend.Logistics.machinery.application.commands.UpdateMachineryCommand;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.shared.application.result.Result;

public interface MachineryCommandService {

    Result<Machinery, MachineryCommandFailure> handle(CreateMachineryCommand command);

    Result<Machinery, MachineryCommandFailure> handleUpdate(String id, UpdateMachineryCommand command);

    Result<Machinery, MachineryCommandFailure> handlePatch(String id, UpdateMachineryCommand command);

    Result<Void, MachineryCommandFailure> handleDelete(String id);
}
