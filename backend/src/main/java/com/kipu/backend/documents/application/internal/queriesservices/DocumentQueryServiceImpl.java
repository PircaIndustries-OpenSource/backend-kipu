package com.kipu.backend.documents.application.internal.queriesservices;

import com.kipu.backend.documents.application.queries.GetAllDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllPendingDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllSignedDocumentsQuery;
import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Service
public class DocumentQueryServiceImpl implements DocumentQueryService {

    private final DocumentRepository documentRepository;

    public DocumentQueryServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> handle(GetAllDocumentsQuery query) {
        return this.documentRepository.findByProjectId(query.projectId());
    }

    @Override
    public List<Document> handle(GetAllPendingDocumentsQuery query) {
        return this.documentRepository.findByIsSignedFalse(query.projectId());
    }

    @Override
    public List<Document> handle(GetAllSignedDocumentsQuery query) {
        return this.documentRepository.findByIsSignedTrue(query.projectId());
    }
}
