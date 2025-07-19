package com.safetynet.service.implementations;

import com.safetynet.model.Person;
import com.safetynet.repository.interfaces.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private List<Person> mockPersons;
    private Person person1;
    private Person person2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        person1 = new Person("John", "Doe", "1509 Culver St", "Paris", "75000", "123-456-7890", "john@doe.com");
        person2 = new Person("Jane", "Doe", "1509 Culver St", "Paris", "75000", "987-654-3210", "jane@doe.com");

        mockPersons = new ArrayList<>();
        mockPersons.add(person1);
        mockPersons.add(person2);
    }

    @Test
    public void testCreatePerson() {
        Person newPerson = new Person("New", "Person", "1 Rue Test", "Paris", "75000", "555-0000", "new@person.com");
        when(personRepository.getAll()).thenReturn(mockPersons);
        boolean created = personService.addPerson(newPerson);

        assertTrue(created);
        verify(personRepository).createNewPerson(newPerson);
    }

    @Test
    public void testCreatePersonDuplicate() {
        Person duplicate = new Person("John", "Doe", "Diff Address", "Paris", "00000", "000-000-0000", "other@mail.com");
        when(personRepository.findByFullName(anyString(), anyString())).thenReturn(duplicate);
        boolean created = personService.addPerson(duplicate);

        assertFalse(created);
        verify(personRepository, never()).createNewPerson(any());
    }

    @Test
    public void testUpdatePerson() {
        Person updated = new Person("John", "Doe", "1509 Culver St", "Paris", "75000", "999-999-9999", "new@email.com");
        when(personRepository.findByFullName(anyString(), anyString())).thenReturn(person1);
        boolean updatedResult = personService.updatePerson(updated);

        assertTrue(updatedResult);
        verify(personRepository).updatePerson(updated);
    }

    @Test
    public void testUpdatePersonNotFound() {
        when(personRepository.findByFullName(anyString(), anyString())).thenReturn(null);
        Person unknown = new Person("Ghost", "Guy", "N/A", "Nowhere", "00000", "000-000-0000", "ghost@guy.com");
        boolean updated = personService.updatePerson(unknown);

        assertFalse(updated);
    }

    @Test
    public void testDeletePerson() {
        when(personRepository.findByFullName(anyString(), anyString())).thenReturn(person2);
        boolean deleted = personService.deletePerson("Jane", "Doe");

        assertTrue(deleted);
        verify(personRepository).deletePersonByFullName("Jane", "Doe");
    }

    @Test
    public void testDeletePersonNotFound() {
        when(personRepository.findByFullName(anyString(), anyString())).thenReturn(null);
        boolean deleted = personService.deletePerson("Ghost", "Guy");

        assertFalse(deleted);
    }
}
