package com.safetynet.service.interfaces;

import com.safetynet.model.Person;

import java.util.List;

public interface PersonService {
    Person getPersonByFullName(String firstName, String lastName);

    List<Person> getPersonsByAddress(String address);

    List<Person> getPersonsByLastName(String lastName);

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(String firstName, String lastName);
}
