package com.kipu.backend.Logistics.machinery.domain.repositories;

import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;

import java.util.List;
import java.util.Optional;

public interface MachineryRepository {
    Machinery save(Machinery machinery);
    Optional<Machinery> findById(String id);
    List<Machinery> findByProjectId(String projectId);
    List<Machinery> findAll();
    boolean existsById(String id);
    void deleteById(String id);
}
