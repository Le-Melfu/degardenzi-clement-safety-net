package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.Person;
import com.safetynet.repository.interfaces.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonInMemoryRepository implements PersonRepository {


    @Override
    public List<Person> getAll() {
        return FakeDatabase.getPersons();
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        List<Person> persons = FakeDatabase.getPersons();
        return persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Person> findByAddress(String address) {
        List<Person> persons = FakeDatabase.getPersons();
        return persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        List<Person> persons = FakeDatabase.getPersons();
        return persons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    @Override
    public void createNewPerson(Person person) {
        List<Person> persons = FakeDatabase.getPersons();
        boolean exists = persons.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName()));
        if (!exists) {
            persons.add(person);
        }
    }

    @Override
    public void updatePerson(Person person) {
        List<Person> persons = FakeDatabase.getPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person existing = persons.get(i);
            if (existing.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                    existing.getLastName().equalsIgnoreCase(person.getLastName())) {
                persons.set(i, person);
                return;
            }
        }
    }

    @Override
    public void deletePersonByFullName(String firstName, String lastName) {
        List<Person> persons = FakeDatabase.getPersons();
        persons.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName));
    }
}
