package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonInMemoryRepositoryTest {

    private PersonInMemoryRepository personRepository;
    private FakeDatabase fakeDatabase;

    @BeforeEach
    public void setUp() {
        fakeDatabase = new FakeDatabase();
        personRepository = new PersonInMemoryRepository(fakeDatabase);

        fakeDatabase.setPersonData(List.of(
                new Person("John", "Doe", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@doe.com"),
                new Person("Jane", "Doe", "29 15th St", "Culver", "97451", "841-874-6513", "jane@doe.com")
        ));
    }

    @Test
    public void testFindAll() {
        List<Person> people = personRepository.getAll();
        assertEquals(2, people.size());
    }

    @Test
    public void testSaveAndFindByFullName() {
        Person result = personRepository.findByFullName("John", "Doe");
        assertNotNull(result);
        assertEquals("1509 Culver St", result.getAddress());
    }

    @Test
    public void testCreatePerson() {
        Person newPerson = new Person("Tim", "Doe", "9 rue des Champs Elysées", "Paris", "75000", "000-000", "tim@doe.com");
        personRepository.createNewPerson(newPerson);
        assertNotNull(personRepository.findByFullName("Tim", "Doe"));
    }

    @Test
    public void testUpdatePerson() {
        Person updated = new Person("John", "Doe", "Updated Address", "Paris", "75000", "000-000", "update@mail.com");
        personRepository.updatePerson(updated);
        Person result = personRepository.findByFullName("John", "Doe");
        assertEquals("Updated Address", result.getAddress());
    }

    @Test
    public void testDeletePerson() {
        Person person = personRepository.findByFullName("Jane", "Doe");
        personRepository.deletePersonByFullName(person.getFirstName(), person.getLastName());
        assertNull(personRepository.findByFullName("Jane", "Doe"));
    }
}