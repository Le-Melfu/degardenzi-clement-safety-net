package com.safetynet.model.dto;

import java.util.List;

public record FireIncidentDTO(int stationNumber, List<PersonWithMedicalDataDTO> residents) {
}
