package com.safetynet.model.dto;

import java.util.List;

public record FireIncidentDTO(String stationNumber, List<PersonWithMedicalDataDTO> residents) {
}
