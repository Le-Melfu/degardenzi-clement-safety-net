package com.safetynet.service.implementations;

import com.safetynet.model.Person;
import com.safetynet.repository.interfaces.PersonRepository;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        return personRepository.findByAddress(address);
    }


    @Override
    public boolean addPerson(Person person) {
        if (personRepository.findByFullName(person.getFirstName(), person.getLastName()) == null) {
            personRepository.createNewPerson(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (personRepository.findByFullName(person.getFirstName(), person.getLastName()) != null) {
            personRepository.updatePerson(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePerson(String firstName, String lastName) {
        if (personRepository.findByFullName(firstName, lastName) != null) {
            personRepository.deletePersonByFullName(firstName, lastName);
            return true;
        }
        return false;
    }
}
