package com.safetynet.config.loader;

import com.safetynet.model.Data;
import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@Getter
public class FakeDatabase {

    final JsonDataWriter jsonDataWriter;

    public FakeDatabase(JsonDataWriter jsonDataWriter) {
        this.jsonDataWriter = jsonDataWriter;
    }

    public List<Person> persons = new CopyOnWriteArrayList<>();
    public List<FirestationMapping> firestationMappings = new CopyOnWriteArrayList<>();
    public List<MedicalRecord> medicalrecords = new CopyOnWriteArrayList<>();


    public void setPersonData(List<Person> personsData) {
        this.persons.clear();
        this.persons.addAll(personsData);
    }

    public void setFirestationData(List<FirestationMapping> firestationsData) {
        this.firestationMappings.clear();
        this.firestationMappings.addAll(firestationsData);
    }

    public void setMedicalRecordData(List<MedicalRecord> medicalrecordsData) {
        this.medicalrecords.clear();
        this.medicalrecords.addAll(medicalrecordsData);
    }

    public void writePersonsData(List<Person> persons) {
        this.persons.clear();
        this.persons.addAll(persons);
        saveCurrentState();
    }

    public void writeFirestationsData(List<FirestationMapping> firestationMappings) {
        this.firestationMappings.clear();
        this.firestationMappings.addAll(firestationMappings);
        saveCurrentState();
    }

    public void writeMedicalRecordData(List<MedicalRecord> medicalrecords) {
        this.medicalrecords.clear();
        this.medicalrecords.addAll(medicalrecords);
        saveCurrentState();
    }

    public void saveCurrentState() {
        try {
            Data data = new Data(this.persons, this.firestationMappings, this.medicalrecords);
            jsonDataWriter.saveData(data);
        } catch (Exception e) {
            log.warn("Error saving the data : {}", e.getMessage());
        }
    }

}

