package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands;

/**
 * Create Hopper Watch Command
 */
public record CreateHopperWatchSensorCommand(
        String projectId,
        String sensorId,
        String name,
        String unit,
        Integer state,
        Integer lastLecture,
        Integer limit
) {
}