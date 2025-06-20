package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Data {
    private final List<Person> persons;
    private final List<Firestation> firestations;
    private final List<MedicalRecord> medicalrecords;

    @JsonCreator
    public Data(
            @JsonProperty("persons") List<Person> persons,
            @JsonProperty("firestations") List<Firestation> firestations,
            @JsonProperty("medicalrecords") List<MedicalRecord> medicalrecords
    ) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalrecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }
}
