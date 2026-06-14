package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.Email;
import com.kipu.backend.Logistics.domain.model.valueobjects.Phone;
import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import com.kipu.backend.Logistics.domain.model.valueobjects.SocialReason;

/**
 * Command for creating a supplier.
 *
 * @param ruc           the RUC value object
 * @param socialReason  the social reason value object
 * @param contact       the contact person name (can be null)
 * @param phone         the phone value object
 * @param email         the email value object
 * @param paymentTerms  payment terms (can be null)
 * @param isActive      active flag (default true if null)
 */
public record CreateSupplierCommand(Ruc ruc, SocialReason socialReason, String contact,
                                    Phone phone, Email email, String paymentTerms, Boolean isActive) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if any required parameter is null
     */
    public CreateSupplierCommand {
        if (ruc == null)
            throw new IllegalArgumentException("ruc cannot be null");
        if (socialReason == null)
            throw new IllegalArgumentException("socialReason cannot be null");
        if (phone == null)
            throw new IllegalArgumentException("phone cannot be null");
        if (email == null)
            throw new IllegalArgumentException("email cannot be null");
        // contact, paymentTerms, isActive can be null
    }
}