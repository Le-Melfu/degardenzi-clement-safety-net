package com.safetynet.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = {"firstName", "lastName"})
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Person {

    @NotBlank
    @Size(min = 1, max = 50)
    private final String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String lastName;

    @NotBlank
    private final String address;

    @NotBlank
    private final String city;

    @NotBlank
    private final String zip;

    @NotBlank
    private final String phone;

    @NotBlank
    @Email
    private final String email;
}
