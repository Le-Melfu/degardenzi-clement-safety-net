package com.safetynet.controller;

import com.safetynet.model.Firestation;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.service.interfaces.FirestationService;
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
    @GetMapping(params = "stationNumber")
    public FirestationCoverageDTO getCoverageByStation(@RequestParam String stationNumber) {
        return firestationService.getPersonsCoveredByStation(stationNumber);
    }

    // ➕ POST
    @PostMapping
    public ResponseEntity<Void> createMapping(@RequestBody Firestation firestation) {
        firestationService.createNewFirestation(firestation);
        return ResponseEntity.ok().build();
    }

    // 🔁 PUT — Mettre à jour le numéro de caserne d’une adresse
    @PutMapping
    public ResponseEntity<Void> updateMapping(@RequestBody Firestation firestation) {
        firestationService.updateFirestation(firestation);
        return ResponseEntity.ok().build();
    }

    // ❌ DELETE — Supprimer un mapping par adresse
    @DeleteMapping
    public ResponseEntity<Void> deleteMapping(@RequestParam String address) {
        firestationService.deleteFirestationByAddress(address);
        return ResponseEntity.noContent().build();
    }


}
