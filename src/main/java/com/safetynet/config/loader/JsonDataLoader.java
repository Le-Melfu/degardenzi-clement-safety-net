package com.safetynet.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.Data;
import com.safetynet.repository.implementations.FirestationInMemoryRepository;
import com.safetynet.repository.implementations.MedicalRecordInMemoryRepository;
import com.safetynet.repository.implementations.PersonInMemoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class JsonDataLoader {

    private final PersonInMemoryRepository personRepository;
    private final FirestationInMemoryRepository firestationRepository;
    private final MedicalRecordInMemoryRepository medicalRecordRepository;

    @PostConstruct
    public void loadData() {
        try {
            System.out.println("[INIT] Starting JSON data load...");

            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new ClassPathResource("data/data.json").getInputStream();
            Data data = mapper.readValue(input, Data.class);

            personRepository.setData(data.getPersons());
            firestationRepository.setData(data.getFirestations());
            medicalRecordRepository.setData(data.getMedicalrecords());

            System.out.printf(
                    "[INIT] JSON data loaded successfully: %d persons, %d firestations, %d medical records.%n",
                    data.getPersons().size(),
                    data.getFirestations().size(),
                    data.getMedicalrecords().size()
            );

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load JSON data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
