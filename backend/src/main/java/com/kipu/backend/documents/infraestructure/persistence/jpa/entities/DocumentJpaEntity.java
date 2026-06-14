package com.kipu.backend.documents.infraestructure.persistence.jpa.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Document")
@Table(name = "document")
@Getter
@Setter
public class DocumentJpaEntity {

    @Id
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "is_signed")
    private boolean isSigned;

    @Column(name = "digital_signature_token")
    private String digitalSignatureToken;

    @Column(name = "deadline")
    private LocalDateTime deadLine;

    @Column(name = "project_id")
    private String projectId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id")
    private List<DocumentSignerJpaEntity> signers = new ArrayList<>();



}
