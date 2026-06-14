package com.kipu.backend.teamworkers.application.commands;


import java.util.List;

public record CreateTeamWorkerCommand(
        String dni,
        String fullName,
        String role,
        String projectId,
        List<TeamWorkerMachineryItem> machineries
) {}