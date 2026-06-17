package com.kipu.backend.budget.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "expense_histories")
@Getter
@Setter
public class ExpenseHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String concept;
    private Double amount;
    private String responsible;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}