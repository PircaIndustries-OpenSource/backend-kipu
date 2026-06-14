package com.kipu.backend.documents.infraestructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "document_signers")
@Getter
@Setter
public class DocumentSignerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "team_user_id")
    private String teamUserId;

    @Column(name = "full_name")
    private String fullName;


}
