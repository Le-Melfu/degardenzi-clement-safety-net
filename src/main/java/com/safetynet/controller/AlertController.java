package com.safetynet.controller;

import com.safetynet.model.dto.ChildAlertDTO;
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


}
