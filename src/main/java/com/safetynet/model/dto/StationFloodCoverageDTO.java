package com.safetynet.model.dto;

import java.util.List;


public record StationFloodCoverageDTO(String stationNumber, List<HouseholdWithMedicalDataDTO> households) {
}
