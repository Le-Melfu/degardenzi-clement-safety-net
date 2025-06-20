package com.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Data {
    private final List<Person> persons;
    private final List<Firestation> firestations;
    private final List<MedicalRecord> medicalrecords;
}
