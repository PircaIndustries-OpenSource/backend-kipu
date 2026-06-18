package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;

/**
 * Interface layer translator converting string values from HTTP inputs into domain value objects.
 */
public class LogisticsValueObjectFromStringAssembler {

    public static Name toNameFromString(String value) {
        return new Name(value);
    }

    public static CategoryId toCategoryIdFromString(String value) {
        return new CategoryId(Integer.parseInt(value));
    }

    public static CategoryId toCategoryIdFromInteger(Integer value) {
        return new CategoryId(value);
    }

    public static MeasureUnit toMeasureUnitFromString(String value) {
        return MeasureUnit.valueOf(value.toUpperCase());
    }

    public static MaterialCatalogId toMaterialCatalogIdFromInteger(Integer value) {
        return new MaterialCatalogId(value);
    }

    public static Quantity toQuantityFromInteger(Integer value) {
        return new Quantity(value);
    }

    public static WarehouseLocation toWarehouseLocationFromString(String value) {
        return new WarehouseLocation(value);
    }

    public static ProjectId toProjectIdFromInteger(Integer value) {
        return new ProjectId(value);
    }

    public static BudgetLineId toBudgetLineIdFromInteger(Integer value) {
        return value == null ? null : new BudgetLineId(value);
    }

    public static UserId toUserIdFromInteger(Integer value) {
        return new UserId(value);
    }

    public static RequestPriority toRequestPriorityFromString(String value) {
        return RequestPriority.valueOf(value.toUpperCase());
    }

    public static RequestStatus toRequestStatusFromString(String value) {
        return RequestStatus.valueOf(value.toUpperCase());
    }

    public static Ruc toRucFromString(String value) {
        return new Ruc(value);
    }

    public static SocialReason toSocialReasonFromString(String value) {
        return new SocialReason(value);
    }

    public static Phone toPhoneFromString(String value) {
        return new Phone(value);
    }

    public static Email toEmailFromString(String value) {
        return new Email(value);
    }

    public static SupplierId toSupplierIdFromInteger(Integer value) {
        return new SupplierId(value);
    }
}