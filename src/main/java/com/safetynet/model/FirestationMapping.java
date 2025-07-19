package com.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = {"address", "station"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FirestationMapping {

    @NotBlank
    private final String address;

    @NotBlank
    private final String station;
}
