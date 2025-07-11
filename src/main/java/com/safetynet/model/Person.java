package com.safetynet.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = {"firstName", "lastName"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Person {
    // TODO Use validation using jakarta constraints
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String zip;
    private final String phone;
    private final String email;
}
