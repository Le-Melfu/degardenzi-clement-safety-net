package com.safetynet.config.loader;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Getter
public class FakeDatabase {
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
}

