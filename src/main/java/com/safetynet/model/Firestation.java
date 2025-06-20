package com.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "address")
@AllArgsConstructor
public class Firestation {
    private final String address;
    private final String station;
}
