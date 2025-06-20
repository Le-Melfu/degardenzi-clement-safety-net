package com.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = {"firstName", "lastName"})
@AllArgsConstructor
public class MedicalRecord {
    private final String firstName;
    private final String lastName;
    private final String birthdate;
    private final List<String> medications;
    private final List<String> allergies;
}
