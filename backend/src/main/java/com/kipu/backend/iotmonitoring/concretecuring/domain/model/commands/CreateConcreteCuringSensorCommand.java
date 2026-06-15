package com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands;

/**
 * Command to initiate a concrete curing monitoring process.
 */
public record CreateConcreteCuringSensorCommand(String projectId,
                                                String sensorId,
                                                Integer state,
                                                String location,
                                                Double temperature,
                                                String unit,
                                                Integer humidity,
                                                Double limit) {
}
