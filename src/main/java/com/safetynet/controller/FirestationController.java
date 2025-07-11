package com.safetynet.controller;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.service.interfaces.FirestationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // 🔍 GET /firestation?stationNumber=X
    @Operation(
            summary = "Get persons covered by a firestation",
            description = "Returns a list of persons with basic public info for a given firestation number, along with adult and child counts."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coverage data returned successfully"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid station number"),
            @ApiResponse(responseCode = "404", description = "Station was not found")
    })
    @GetMapping(params = "stationNumber")
    public ResponseEntity<FirestationCoverageDTO> getCoverageByStation(@RequestParam String stationNumber) {
        FirestationCoverageDTO response = firestationService.getPersonsCoveredByStation(stationNumber);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity.ok(response);
    }

    // ➕ POST — Create new mapping
    @Operation(
            summary = "Add a firestation mapping",
            description = "Creates a new firestation-to-address mapping."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Firestation Mapping created successfully"),
            @ApiResponse(responseCode = "409", description = "Firestation Mapping already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> createMapping(@RequestBody FirestationMapping firestationMapping) {
        boolean created = firestationService.createNewFirestation(firestationMapping);
        return created
                ? ResponseEntity.status(201).build()
                : ResponseEntity.status(409).build();
    }

    // 🔁 PUT — Update station number for an address
    @Operation(
            summary = "Update a firestation mapping",
            description = "Updates the station number for a given address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firestation Mapping updated successfully"),
            @ApiResponse(responseCode = "404", description = "Firestation Mapping not found for the provided address"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping
    public ResponseEntity<Void> updateMapping(@RequestBody FirestationMapping firestationMapping) {
        boolean updated = firestationService.updateFirestation(firestationMapping);
        return updated
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // ❌ DELETE — Delete mapping by address
    @Operation(
            summary = "Delete a firestation mapping",
            description = "⚠️ This operation removes the firestation assignment for the specified address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Firestation Mapping successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Firestation Mapping not found for the provided address")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteMapping(@RequestParam String address) {
        boolean deleted = firestationService.deleteFirestationByAddress(address);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
