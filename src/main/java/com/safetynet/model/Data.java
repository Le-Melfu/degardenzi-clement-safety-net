package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Data {
    private final List<Person> persons;
    private final List<FirestationMapping> firestationMappings;
    private final List<MedicalRecord> medicalrecords;

    @JsonCreator
    public Data(
            @JsonProperty("persons") List<Person> persons,
            @JsonProperty("firestations") List<FirestationMapping> firestationMappings,
            @JsonProperty("medicalrecords") List<MedicalRecord> medicalrecords
    ) {
        this.persons = persons;
        this.firestationMappings = firestationMappings;
        this.medicalrecords = medicalrecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<FirestationMapping> getFirestations() {
        return firestationMappings;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }
}
