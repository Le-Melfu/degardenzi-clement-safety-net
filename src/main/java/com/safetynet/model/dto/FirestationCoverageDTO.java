package com.safetynet.model.dto;

import java.util.List;

public record FirestationCoverageDTO(List<PersonPublicInfosDTO> persons, int adultCount, int childCount) {
}
