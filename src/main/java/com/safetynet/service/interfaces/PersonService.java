package com.safetynet.service.interfaces;

import com.safetynet.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPersons();

    List<Person> getPersonsByAddress(String address);

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(String firstName, String lastName);

}
