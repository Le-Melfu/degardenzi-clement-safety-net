package com.safetynet.config.loader;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FakeDatabase {
    @Getter
    public static List<Person> persons = new CopyOnWriteArrayList<>();
    @Getter
    public static List<FirestationMapping> firestationMappings = new CopyOnWriteArrayList<>();
    @Getter
    public static List<MedicalRecord> medicalrecords = new CopyOnWriteArrayList<>();

    public static void setPersonData(List<Person> personsData) {
        persons.clear();
        persons.addAll(personsData);
    }

    public static void setFirestationData(List<FirestationMapping> firestationsData) {
        firestationMappings.clear();
        firestationMappings.addAll(firestationsData);
    }

    public static void setMedicalRecordData(List<MedicalRecord> medicalrecordsData) {
        medicalrecords.clear();
        medicalrecords.addAll(medicalrecordsData);
    }
}

