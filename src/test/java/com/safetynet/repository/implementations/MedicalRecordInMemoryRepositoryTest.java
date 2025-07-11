package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordInMemoryRepositoryTest {

    private FakeDatabase fakeDatabase;
    private MedicalRecordInMemoryRepository medicalRecordRepository;

    @BeforeEach
    public void setUp() {
        fakeDatabase = new FakeDatabase();
        medicalRecordRepository = new MedicalRecordInMemoryRepository(fakeDatabase);

        fakeDatabase.setMedicalRecordData(List.of(
                new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1:100mg"), List.of("peanut")),
                new MedicalRecord("Jane", "Doe", "02/02/1985", List.of("med2:200mg"), List.of("gluten"))
        ));
    }

    @Test
    public void testFindByFullName() {
        MedicalRecord record = medicalRecordRepository.findByFullName("John", "Doe");
        assertNotNull(record);
        assertEquals("01/01/1990", record.getBirthdate());
    }

    @Test
    public void testFindByFullNameNotFound() {
        MedicalRecord record = medicalRecordRepository.findByFullName("Ghost", "Guy");
        assertNull(record);
    }

    @Test
    public void testGetBirthdateByFullName() {
        String birthdate = medicalRecordRepository.getBirthdateByFullName("Jane", "Doe");
        assertEquals("02/02/1985", birthdate);
    }

    @Test
    public void testGetBirthdateByFullNameNotFound() {
        String birthdate = medicalRecordRepository.getBirthdateByFullName("Foo", "Bar");
        assertNull(birthdate);
    }

    @Test
    public void testCreateNewMedicalRecord() {
        MedicalRecord newRecord = new MedicalRecord("New", "Person", "03/03/2000", List.of("med3:300mg"), List.of("dust"));
        medicalRecordRepository.createNewMedicalRecord(newRecord);

        MedicalRecord fetched = medicalRecordRepository.findByFullName("New", "Person");
        assertNotNull(fetched);
        assertEquals("03/03/2000", fetched.getBirthdate());
    }

    @Test
    public void testCreateNewMedicalRecordDuplicate() {
        MedicalRecord duplicate = new MedicalRecord("John", "Doe", "04/04/1999", List.of("med4:400mg"), List.of("latex"));
        medicalRecordRepository.createNewMedicalRecord(duplicate);

        List<MedicalRecord> all = fakeDatabase.getMedicalrecords();
        assertEquals(2, all.size()); // should not have been added
    }

    @Test
    public void testUpdateMedicalRecord() {
        MedicalRecord updated = new MedicalRecord("Jane", "Doe", "05/05/1980", List.of("newmed"), List.of("none"));
        medicalRecordRepository.updateMedicalRecord(updated);

        MedicalRecord result = medicalRecordRepository.findByFullName("Jane", "Doe");
        assertNotNull(result);
        assertEquals("05/05/1980", result.getBirthdate());
        assertEquals(List.of("newmed"), result.getMedications());
    }

    @Test
    public void testUpdateMedicalRecordNotFound() {
        MedicalRecord ghost = new MedicalRecord("Ghost", "Guy", "06/06/1970", List.of("ghostmed"), List.of("ghostallergy"));
        medicalRecordRepository.updateMedicalRecord(ghost);

        assertNull(medicalRecordRepository.findByFullName("Ghost", "Guy"));
    }

    @Test
    public void testDeleteMedicalRecordByFullName() {
        medicalRecordRepository.deleteMedicalRecordByFullName("John", "Doe");

        assertNull(medicalRecordRepository.findByFullName("John", "Doe"));
    }

    @Test
    public void testDeleteMedicalRecordByFullNameNotFound() {
        medicalRecordRepository.deleteMedicalRecordByFullName("Ghost", "Guy");

        List<MedicalRecord> all = fakeDatabase.getMedicalrecords();
        assertEquals(2, all.size());
    }
}
