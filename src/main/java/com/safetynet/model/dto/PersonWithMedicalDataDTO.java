package com.safetynet.model.dto;

import java.util.List;

public record PersonWithMedicalDataDTO(String lastName, String phone, int age, List<String> medications,
                                       List<String> allergies) {
}
