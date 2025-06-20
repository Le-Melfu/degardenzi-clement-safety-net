package com.safetynet.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Firestation {

    private final String address;
    private final String station;

    /**
     * Constructor used only to deserialize data from JSON
     */
    @JsonCreator
    public Firestation(
            @JsonProperty("address") String address,
            @JsonProperty("station") String station
    ) {
        this.address = address;
        this.station = station;
    }
}