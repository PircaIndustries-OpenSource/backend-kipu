package com.kipu.backend.documents.infraestructure.persistence.jpa.mappers;

import com.kipu.backend.documents.domain.model.aggregates.Document;
import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import com.kipu.backend.documents.infraestructure.persistence.jpa.entities.DocumentJpaEntity;
import com.kipu.backend.documents.infraestructure.persistence.jpa.entities.DocumentSignerJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentMapper {

    public DocumentJpaEntity toJpa(Document document) {

        DocumentJpaEntity entity = new DocumentJpaEntity();
        entity.setId(document.getId());
        entity.setType(document.getType());
        entity.setSigned(document.isSigned());
        entity.setDeadLine(document.getDeadLine());
        entity.setProjectId(document.getProjectId());
        entity.setDigitalSignatureToken(document.getDigitalSignatureToken());

        List<DocumentSignerJpaEntity> signers = document.getSigners().stream().map(s -> {
            DocumentSignerJpaEntity signer = new DocumentSignerJpaEntity();
            signer.setDocumentId(document.getId());
            signer.setFullName(s.fullName());
            signer.setTeamUserId(s.teamUserId());
            return signer;
        }).toList();

        entity.setSigners(signers);
        return entity;
    }

    public Document toDomain(DocumentJpaEntity document) {

        List<Signer> signers = document.getSigners().stream().map(s ->
                   new Signer(s.getTeamUserId(), s.getFullName())

        ).toList();

        return new Document(document.getId(), document.getType(), document.isSigned(),
                document.getDigitalSignatureToken(), document.getDeadLine(), document.getProjectId(), signers);

    }
}
