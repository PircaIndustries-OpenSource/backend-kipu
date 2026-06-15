package com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands;

public record CreateConcreteCuringSensorCommand(String projectId,
                                                String sensorId,
                                                Integer state,
                                                String location,
                                                Double temperature,
                                                String unit,
                                                Integer humidity,
                                                Double limit) {
}
