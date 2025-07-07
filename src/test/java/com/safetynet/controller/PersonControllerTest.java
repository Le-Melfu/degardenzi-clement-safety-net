package com.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.config.PersonControllerTestConfig;
import com.safetynet.model.Person;
import com.safetynet.service.interfaces.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@Import(PersonControllerTestConfig.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPerson_shouldReturn200_whenSuccess() throws Exception {
        Person newPerson = new Person("Jane", "Doe", "...", "...", "...", "...", "jane@doe.com");
        when(personService.addPerson(any())).thenReturn(true);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isOk());
    }

    @Test
    void createPerson_shouldReturn409_whenAlreadyExists() throws Exception {
        Person newPerson = new Person("Jane", "Doe", "...", "...", "...", "...", "jane@doe.com");
        when(personService.addPerson(any())).thenReturn(false);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isConflict());
    }

    @Test
    void updatePerson_shouldReturn200_whenUpdated() throws Exception {
        Person updated = new Person("John", "Doe", "...", "...", "...", "...", "john@doe.com");
        when(personService.updatePerson(any())).thenReturn(true);

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson_shouldReturn404_whenNotFound() throws Exception {
        Person updated = new Person("Ghost", "User", "...", "...", "...", "...", "ghost@mail.com");
        when(personService.updatePerson(any())).thenReturn(false);

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePerson_shouldReturn204_whenDeleted() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerson_shouldReturn404_whenNotFound() throws Exception {
        when(personService.deletePerson("Ghost", "User")).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .param("firstName", "Ghost")
                        .param("lastName", "User"))
                .andExpect(status().isNotFound());
    }
}

