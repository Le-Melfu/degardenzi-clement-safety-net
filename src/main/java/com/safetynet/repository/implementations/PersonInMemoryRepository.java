package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.Person;
import com.safetynet.repository.interfaces.PersonRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonInMemoryRepository implements PersonRepository {

    private final FakeDatabase fakeDatabase;

    public PersonInMemoryRepository(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    @Override
    public List<Person> getAll() {
        return fakeDatabase.getPersons();
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        return fakeDatabase.getPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return fakeDatabase.getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
    }

    @Override
    public void createNewPerson(Person person) {
        List<Person> current = new ArrayList<>(fakeDatabase.getPersons());
        boolean exists = current.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName()));
        if (!exists) {
            current.add(person);
            fakeDatabase.writePersonsData(current);
        }
    }

    @Override
    public void updatePerson(Person person) {
        List<Person> current = new ArrayList<>(fakeDatabase.getPersons());
        boolean updated = false;

        for (int i = 0; i < current.size(); i++) {
            Person existing = current.get(i);
            if (existing.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                    existing.getLastName().equalsIgnoreCase(person.getLastName())) {
                current.set(i, person);
                updated = true;
                break;
            }
        }

        if (updated) {
            fakeDatabase.writePersonsData(current);
        }
    }

    @Override
    public void deletePersonByFullName(String firstName, String lastName) {
        List<Person> current = new ArrayList<>(fakeDatabase.getPersons());
        boolean removed = current.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            fakeDatabase.writePersonsData(current);
        }
    }
}