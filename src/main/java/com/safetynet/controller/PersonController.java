package com.safetynet.controller;

import com.safetynet.model.Person;
import com.safetynet.service.interfaces.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Add a new person",
            description = "Creates a new person in the system. If the person already exists, the request will be ignored."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person successfully created"),
            @ApiResponse(responseCode = "409", description = "Person already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody @Valid Person person) {
        boolean created = personService.addPerson(person);
        if (!created) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).build();
    }

    // 🔁 PUT
    @Operation(
            summary = "Update an existing person",
            description = "Updates an existing person's information. First name and last name are used as identifiers."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person successfully updated"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PutMapping
    public ResponseEntity<Void> updatePerson(@RequestBody @Valid Person updatedPerson) {
        boolean updated = personService.updatePerson(updatedPerson);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    // ❌ DELETE
    @Operation(
            summary = "Delete a person",
            description = "⚠️ This operation is irreversible. The person will be permanently removed from the registry."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePerson(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        boolean deleted = personService.deletePerson(firstName, lastName);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
