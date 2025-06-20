package com.safetynet.model.dto;

import java.util.List;


public record ChildAlertDTO(List<ChildDTO> children, List<PersonPublicInfosDTO> otherMembers) {
}
