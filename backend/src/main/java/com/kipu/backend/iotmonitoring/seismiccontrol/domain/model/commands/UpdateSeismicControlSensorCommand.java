package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands;

public record UpdateSeismicControlSensorCommand(
        Long id,
        String sensorId,
        String unit,
        Double lastLecture,
        Double limit,
        String location,
        String timeLecture,
        Integer state) {
    public UpdateSeismicControlSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Unit is required");
    }
}