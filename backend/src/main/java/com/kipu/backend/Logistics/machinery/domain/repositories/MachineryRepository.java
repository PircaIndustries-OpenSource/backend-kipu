package com.kipu.backend.logistics.machinery.domain.repositories;

import com.kipu.backend.logistics.machinery.domain.model.entities.Machinery;

import java.util.List;
import java.util.Optional;

public interface MachineryRepository {
    Machinery save(Machinery machinery);
    Optional<Machinery> findById(String id);
    List<Machinery> findByProjectId(String projectId);
    void deleteById(String id);
}
