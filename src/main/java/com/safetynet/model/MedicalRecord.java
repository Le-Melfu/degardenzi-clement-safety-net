package com.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = {"firstName", "lastName"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MedicalRecord {

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @NotBlank
    @Pattern(
            regexp = "^\\d{2}/\\d{2}/\\d{4}$",
            message = "Birthdate must be in the format dd/MM/yyyy"
    )
    private final String birthdate;

    @NotNull
    private final List<@NotBlank String> medications;

    @NotNull
    private final List<@NotBlank String> allergies;
}
