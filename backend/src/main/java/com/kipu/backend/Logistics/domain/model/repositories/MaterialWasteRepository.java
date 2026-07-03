package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

import java.util.List;
import java.util.Optional;

public interface MaterialWasteRepository {
    MaterialWaste save(MaterialWaste materialWaste);
    Optional<MaterialWaste> findById(Long id);
    List<MaterialWaste> findAll();
    List<MaterialWaste> findByProjectId(ProjectId projectId);
    boolean existsById(Long id);
    void deleteById(Long id);
}
