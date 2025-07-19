package com.safetynet.controller;

import com.safetynet.model.MedicalRecord;
import com.safetynet.service.interfaces.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // ➕ POST — Add a medical record
    @Operation(
            summary = "Add a medical record",
            description = "Creates a new medical record for a person. First and last names are used as identifiers."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medical record created successfully"),
            @ApiResponse(responseCode = "409", description = "Medical record already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Void> createMedicalRecord(@RequestBody @Valid MedicalRecord record) {
        boolean created = medicalRecordService.addMedicalRecord(record);
        return created
                ? ResponseEntity.status(201).build()
                : ResponseEntity.status(409).build();
    }

    // 🔁 PUT — Update an existing record
    @Operation(
            summary = "Update a medical record",
            description = "Updates an existing medical record. First and last names are used to find the record."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Medical record not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody @Valid MedicalRecord record) {
        boolean updated = medicalRecordService.updateMedicalRecord(record);
        return updated
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // ❌ DELETE — Delete a medical record
    @Operation(
            summary = "Delete a medical record",
            description = "⚠️ This operation permanently deletes the medical record for the specified person."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medical record successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Medical record not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
