package com.safetynet.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = {"address", "station"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FirestationMapping {
    private final String address;
    private final String station;
}
