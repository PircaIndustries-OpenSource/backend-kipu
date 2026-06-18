package com.kipu.backend.documents.domain.model.aggregates;

import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
public class Document {

    private final String id;
    String type;
    boolean isSigned;
    String digitalSignatureToken;
    LocalDateTime deadLine;
    String projectId;
    private final List<Signer> signers;

    public Document(String id, String type, LocalDateTime deadLine, String projectId) {
        this.id = id;
        this.type = type;
        this.deadLine = deadLine;
        this.isSigned = false;
        this.projectId = projectId;
        this.signers = new ArrayList<>();
    }

    public Document(String id, String type, boolean isSigned, String digitalSignatureToken, LocalDateTime deadLine, String projectId, List<Signer> signers) {
        this.id = id;
        this.type = type;
        this.isSigned = isSigned;
        this.digitalSignatureToken = digitalSignatureToken;
        this.deadLine = deadLine;
        this.projectId = projectId;
        this.signers = signers;
    }

    public void assignSigner(String teamUserId, String fullName) {
        if (this.isSigned) {
            throw new IllegalStateException("document.validation.assignToSigned");
        }

        boolean alreadyAssigned = signers.stream()
                .anyMatch(s -> s.teamUserId().equals(teamUserId));

        if (alreadyAssigned) {
            throw new IllegalArgumentException("document.validation.userAlreadyAssigned");
        }

        this.signers.add(new Signer(teamUserId, fullName));
    }

    public void markAsSigned() {
        if (this.signers.isEmpty()) {
            throw new IllegalStateException("document.validation.signWithoutSigners");
        }
        this.isSigned = true;
        this.digitalSignatureToken = "SIGN-" + System.currentTimeMillis();
    }

    public List<Signer> getSigners() {
        return Collections.unmodifiableList(signers);
    }
}
