package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MedicalRecord {

    private final String firstName;
    private final String lastName;
    private final String birthdate;
    private final List<String> medications;
    private final List<String> allergies;

    /**
     * Constructor used only to deserialize data from JSON
     */
    @JsonCreator
    public MedicalRecord(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("birthdate") String birthdate,
            @JsonProperty("medications") List<String> medications,
            @JsonProperty("allergies") List<String> allergies
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
}
