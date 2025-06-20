package com.safetynet.repository.implementations;

import com.safetynet.model.Person;
import com.safetynet.repository.interfaces.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonInMemoryRepository implements PersonRepository {

    private final List<Person> persons = new ArrayList<>();

    public void setData(List<Person> data) {
        persons.clear();
        persons.addAll(data);
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        return persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return persons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    @Override
    public void createNewPerson(Person person) {
        boolean exists = persons.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName()));
        if (!exists) {
            persons.add(person);
        }
    }

    @Override
    public void updatePerson(Person person) {
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
        persons.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName));
    }
}
