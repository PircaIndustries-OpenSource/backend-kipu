package com.kipu.backend.rnc.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * JPA Embeddable representing the SolutionLog Value Object.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionLogEmbeddable {
    private Date logDate;
    private String note;
    private String authorId;
}