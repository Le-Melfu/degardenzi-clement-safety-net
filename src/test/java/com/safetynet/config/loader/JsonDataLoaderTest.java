package com.safetynet.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.Data;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonDataLoaderTest {

    @Test
    public void testLoadData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new ClassPathResource("data/data.json").getInputStream();
        Data data = mapper.readValue(input, Data.class);

        assertNotNull(data.getPersons());
        assertFalse(data.getPersons().isEmpty(), "Persons should be loaded");

        assertNotNull(data.getFirestationMappings());
        assertFalse(data.getFirestationMappings().isEmpty(), "Firestations should be loaded");

        assertNotNull(data.getMedicalrecords());
        assertFalse(data.getMedicalrecords().isEmpty(), "MedicalRecords should be loaded");
    }
}
