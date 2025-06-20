package com.safetynet.model;

import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = {"firstName", "lastName"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MedicalRecord {
    private final String firstName;
    private final String lastName;
    private final String birthdate;
    private final List<String> medications;
    private final List<String> allergies;
}
