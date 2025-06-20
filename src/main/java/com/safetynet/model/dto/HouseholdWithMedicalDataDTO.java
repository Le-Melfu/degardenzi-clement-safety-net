package com.safetynet.model.dto;

import java.util.List;

public record HouseholdWithMedicalDataDTO(String address, List<PersonWithMedicalDataDTO> residents) {
}
