package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.*;

import java.time.Instant;
import java.util.Objects;

public class Supplier {

    private final Long id;
    private final Ruc ruc;
    private final SocialReason socialReason;
    private final String contact;
    private final Phone phone;
    private final Email email;
    private final String paymentTerms;
    private final Boolean isActive;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Supplier(Long id, Ruc ruc, SocialReason socialReason, String contact,
                     Phone phone, Email email, String paymentTerms, Boolean isActive,
                     Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.ruc = Objects.requireNonNull(ruc, "supplier.error.ruc.notBlank");
        this.socialReason = Objects.requireNonNull(socialReason, "supplier.error.socialReason.notBlank");
        this.contact = contact;
        this.phone = Objects.requireNonNull(phone, "supplier.error.phone.notBlank");
        this.email = Objects.requireNonNull(email, "supplier.error.email.notBlank");
        this.paymentTerms = paymentTerms;
        this.isActive = isActive != null ? isActive : true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Supplier create(Ruc ruc, SocialReason socialReason, String contact,
                                  Phone phone, Email email, String paymentTerms) {
        Instant now = Instant.now();
        return new Supplier(null, ruc, socialReason, contact, phone, email, paymentTerms, true, now, now);
    }

    public static Supplier create(Ruc ruc, SocialReason socialReason, String contact,
                                  Phone phone, Email email, String paymentTerms, Boolean isActive) {
        Instant now = Instant.now();
        return new Supplier(null, ruc, socialReason, contact, phone, email, paymentTerms, isActive, now, now);
    }

    public static Supplier rehydrate(Long id, Ruc ruc, SocialReason socialReason, String contact,
                                     Phone phone, Email email, String paymentTerms, Boolean isActive,
                                     Instant createdAt, Instant updatedAt) {
        return new Supplier(id, ruc, socialReason, contact, phone, email, paymentTerms, isActive, createdAt, updatedAt);
    }

    public Supplier update(SocialReason socialReason, String contact, Phone phone, Email email,
                           String paymentTerms, Boolean isActive) {
        return new Supplier(
                this.id, this.ruc,
                socialReason != null ? socialReason : this.socialReason,
                contact != null ? contact : this.contact,
                phone != null ? phone : this.phone,
                email != null ? email : this.email,
                paymentTerms != null ? paymentTerms : this.paymentTerms,
                isActive != null ? isActive : this.isActive,
                this.createdAt, Instant.now()
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Ruc getRuc() {
        return ruc;
    }

    public SocialReason getSocialReason() {
        return socialReason;
    }

    public String getContact() {
        return contact;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}