package com.safetynet.config.loader;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class JsonDataLoaderTest {

    @Autowired
    private FakeDatabase fakeDatabase;

    @Test
    public void testLoadData_shouldPopulateDatabase() {
        List<Person> persons = fakeDatabase.getPersons();
        List<FirestationMapping> stations = fakeDatabase.getFirestationMappings();
        List<MedicalRecord> records = fakeDatabase.getMedicalrecords();

        assertFalse(persons.isEmpty(), "Persons should be loaded");
        assertFalse(stations.isEmpty(), "Stations should be loaded");
        assertFalse(records.isEmpty(), "MedicalRecords should be loaded");
    }
}
