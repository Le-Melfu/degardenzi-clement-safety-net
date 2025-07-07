package com.safetynet.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.Data;
import com.safetynet.repository.implementations.FirestationInMemoryRepository;
import com.safetynet.repository.implementations.MedicalRecordInMemoryRepository;
import com.safetynet.repository.implementations.PersonInMemoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonDataLoader {

    private final FakeDatabase fakeDatabase;
    private final PersonInMemoryRepository personRepository;
    private final FirestationInMemoryRepository firestationRepository;
    private final MedicalRecordInMemoryRepository medicalRecordRepository;

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new ClassPathResource("data/data.json").getInputStream();
            Data data = mapper.readValue(input, Data.class);

            fakeDatabase.setPersonData(data.getPersons());
            fakeDatabase.setFirestationData(data.getFirestationMappings());
            fakeDatabase.setMedicalRecordData(data.getMedicalrecords());


            log.info("[INIT] JSON data loaded successfully: {} persons, {} firestations, {} medical records.", data.getPersons().size(), data.getFirestationMappings().size(), data.getMedicalrecords().size());
        } catch (Exception e) {
            log.error("[ERROR] Failed to load JSON data: {}", e.getMessage());
        }
    }
}
