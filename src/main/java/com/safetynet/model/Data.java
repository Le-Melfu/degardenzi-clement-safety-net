package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
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

}
