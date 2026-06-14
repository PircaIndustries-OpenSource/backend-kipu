package com.kipu.backend.documents.domain.model.repositories;

import com.kipu.backend.documents.domain.model.aggregates.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    Document save(Document document);

    Optional<Document> findById(String id);
    List<Document> findByProjectId(String id);
    List<Document> findByIsSignedTrue(String projectId);
    List<Document> findByIsSignedFalse(String projectId);
}
