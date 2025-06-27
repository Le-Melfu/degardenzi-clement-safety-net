package com.safetynet.repository.interfaces;

import com.safetynet.model.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> getAll();

    /**
     * Finds a person by their first and last name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return the corresponding Person if found, or null otherwise
     */
    Person findByFullName(String firstName, String lastName);

    /**
     * Retrieves all persons living at a given address.
     *
     * @param address the address to filter by
     * @return a list of persons living at the specified address
     */
    List<Person> findByAddress(String address);

    /**
     * Adds a new person to the repository.
     *
     * @param person the person to add
     */
    void createNewPerson(Person person);

    /**
     * Updates an existing person.
     * The person is identified by first and last name.
     *
     * @param person the updated person object
     */
    void updatePerson(Person person);

    /**
     * Deletes a person by their first and last name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     */
    void deletePersonByFullName(String firstName, String lastName);
}
