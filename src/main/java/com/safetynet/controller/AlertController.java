package com.safetynet.controller;

import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.FloodCoverageDTO;
import com.safetynet.model.dto.PersonWithMedicalDataDTO;
import com.safetynet.service.interfaces.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }


    //  ../childAlert?address=<address>
    @Operation(
            summary = "Get children living at a specific address",
            description = "Returns a list of children (age ≤ 18) and the other household members at the given address. If there are no child return an empty string"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Children list returned successfully"),
    })
    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenByAddress(@RequestParam String address) {
        ChildAlertDTO dto = alertService.getChildrenByAddress(address);
        return ResponseEntity.ok(dto);
    }


    //  ../phoneAlert?firestation=<firestation_number>
    @Operation(
            summary = "Get phone numbers by firestation",
            description = "Returns a list of phone numbers of all persons covered by the given firestation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone numbers returned successfully"),
            @ApiResponse(responseCode = "204", description = "No residents found for the station number")
    })
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersByStation(@RequestParam String stationNumber) {
        List<String> phones = alertService.getPhoneNumbersByStation(stationNumber);
        return phones.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(phones);
    }


    //  ../fire?address=<address>
    @Operation(
            summary = "Get fire incident data by address",
            description = "Returns a list of residents at the address and the firestation number covering it, including age and medical data."
    )
    @ApiResponse(responseCode = "200", description = "Fire incident data returned")
    @ApiResponse(responseCode = "404", description = "No station found for this adress")
    @GetMapping("/fire")
    public ResponseEntity<FireIncidentDTO> getFireIncident(@RequestParam String address) {
        FireIncidentDTO incident = alertService.getFireIncidentByAddress(address);
        if (incident == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(incident);
    }


    //   ../flood/stations?stations=<a list of station_numbers>
    @Operation(
            summary = "Get flood coverage by multiple firestations",
            description = "Returns grouped households by address with resident medical data."
    )
    @ApiResponse(responseCode = "200", description = "Flood coverage returned")
    @GetMapping("/flood/stations")
    public ResponseEntity<FloodCoverageDTO> getFloodCoverage(@RequestParam List<String> stations) {
        /*
         * pour chaque station passée en paramètre,
         * retourner la liste des habitants desservis par cette station
         * regroupés par adresse
         * avec leurs infos médicales
         * */
    }


    //   ../personInfolastName=<lastName>
    @Operation(
            summary = "Get detailed personal information",
            description = "Returns age, address, email and medical info for matching individuals."
    )
    @ApiResponse(responseCode = "200", description = "Person info returned")
    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonWithMedicalDataDTO>> getPersonInfo(
            @RequestParam String lastName
    ) {
        // TODO: implement personInfo logic
        return ResponseEntity.ok(List.of());
    }


    //  ../communityEmail?city=<city>
    @Operation(
            summary = "Get email addresses by city",
            description = "Returns email addresses of all residents living in the given city."
    )
    @ApiResponse(responseCode = "200", description = "Email list returned")
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailsByCity(@RequestParam String city) {
        // TODO: implement communityEmail logic
        return ResponseEntity.ok(List.of());
    }
}
