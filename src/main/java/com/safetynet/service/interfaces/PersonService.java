package com.safetynet.service.interfaces;

import com.safetynet.model.Person;

import java.util.List;

/**
 * PersonService provides operations to manage and retrieve personal information
 * such as addresses, names, and other attributes related to individuals.
 */
public interface PersonService {

    /**
     * Retrieves all persons stored in the system.
     *
     * @return a list of all {@link Person} objects
     */
    List<Person> getAllPersons();

    /**
     * Retrieves all persons living at the specified address.
     *
     * @param address the address to search for residents
     * @return a list of {@link Person} objects living at the address
     */
    List<Person> getPersonsByAddress(String address);

    /**
     * Adds a new person to the system.
     *
     * @param person the {@link Person} to add
     * @return true if the person was added successfully, false otherwise
     */
    boolean addPerson(Person person);

    /**
     * Updates the information of an existing person.
     *
     * @param person the {@link Person} with updated data
     * @return true if the person was updated successfully, false otherwise
     */
    boolean updatePerson(Person person);

    /**
     * Deletes a person identified by their full name.
     *
     * @param firstName the person's first name
     * @param lastName  the person's last name
     * @return true if the person was deleted successfully, false otherwise
     */
    boolean deletePerson(String firstName, String lastName);
}
