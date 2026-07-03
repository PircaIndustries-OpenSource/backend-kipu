package com.kipu.backend.Logistics.application.commands;

/**
 * Command for deleting a material waste record.
 *
 * @param id the ID of the waste record to delete
 */
public record DeleteMaterialWasteCommand(Long id) {
    public DeleteMaterialWasteCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must be a positive number");
    }
}
