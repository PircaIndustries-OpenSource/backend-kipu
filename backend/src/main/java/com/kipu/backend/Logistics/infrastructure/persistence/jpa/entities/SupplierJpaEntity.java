package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.Email;
import com.kipu.backend.Logistics.domain.model.valueobjects.Phone;
import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import com.kipu.backend.Logistics.domain.model.valueobjects.SocialReason;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.EmailAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.PhoneAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.RucAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.SocialReasonAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "supplier",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ruc", name = "uk_supplier_ruc")
        })
public class SupplierJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ruc", nullable = false, length = 11)
    @Convert(converter = RucAttributeConverter.class)
    private Ruc ruc;

    @Column(name = "social_reason", nullable = false, length = 40)
    @Convert(converter = SocialReasonAttributeConverter.class)
    private SocialReason socialReason;

    @Column(name = "contact", length = 100)
    private String contact;

    @Column(name = "phone", nullable = false, length = 20)
    @Convert(converter = PhoneAttributeConverter.class)
    private Phone phone;

    @Column(name = "email", nullable = false, length = 254)
    @Convert(converter = EmailAttributeConverter.class)
    private Email email;

    @Column(name = "payment_terms", columnDefinition = "TEXT")
    private String paymentTerms;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public SupplierJpaEntity(Long id, Ruc ruc, SocialReason socialReason, String contact,
                             Phone phone, Email email, String paymentTerms, Boolean isActive,
                             Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.ruc = ruc;
        this.socialReason = socialReason;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.paymentTerms = paymentTerms;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}