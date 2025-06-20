package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String zip;
    private final String phone;
    private final String email;


    /**
     * Constructor used only to deserialize data from JSON
     */
    @JsonCreator
    public Person(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") String address,
            @JsonProperty("city") String city,
            @JsonProperty("zip") String zip,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}