package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface MaterialRequestRepository {
    MaterialRequest save(MaterialRequest materialRequest);
    Optional<MaterialRequest> findById(Long id);
    List<MaterialRequest> findByRequestStatus(RequestStatus requestStatus);
    List<MaterialRequest> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}