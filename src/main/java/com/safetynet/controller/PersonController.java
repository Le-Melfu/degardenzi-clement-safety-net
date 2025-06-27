package com.safetynet.controller;

import com.safetynet.model.Person;
import com.safetynet.service.interfaces.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    // ➕ POST
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return ResponseEntity.ok().build();
    }

    // 🔁 PUT
    @PutMapping
    public ResponseEntity<Void> updatePerson(@RequestBody Person updatedPerson) {
        personService.updatePerson(updatedPerson);
        return ResponseEntity.ok().build();

    }

    // ❌ DELETE
    @DeleteMapping
    public ResponseEntity<Void> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
