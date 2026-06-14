package com.kipu.backend.teamworkers.application.commands;

public record AssignMachineryToTeamWorkerCommand(String teamWorkerId, String machineryId, String fullName) {}