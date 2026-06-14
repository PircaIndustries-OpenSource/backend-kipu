package com.kipu.backend.documents.application.internal.queriesservices;

import com.kipu.backend.documents.application.queries.GetAllDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllPendingDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllSignedDocumentsQuery;
import com.kipu.backend.documents.domain.model.aggregates.Document;

import java.util.List;

public interface DocumentQueryService {
    List<Document> handle(GetAllDocumentsQuery query);
    List<Document> handle(GetAllPendingDocumentsQuery query);
    List<Document> handle(GetAllSignedDocumentsQuery query);
}
