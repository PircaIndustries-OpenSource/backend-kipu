package com.kipu.backend.progress.domain.repositories;

import com.kipu.backend.progress.domain.model.entities.ProgressPhoto;

import java.util.List;
import java.util.Optional;

public interface ProgressPhotoRepository {
    ProgressPhoto save(ProgressPhoto photo);
    Optional<ProgressPhoto> findById(Long id);
    List<ProgressPhoto> findByProjectId(String projectId);
    void deleteById(Long id);
}
