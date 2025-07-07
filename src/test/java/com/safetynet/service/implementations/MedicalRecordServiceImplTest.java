package com.safetynet.service.implementations;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.interfaces.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordServiceImplTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    private MedicalRecord record;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        record = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1"));

    }

    @Test
    void testGetBirthdate() {
        when(medicalRecordRepository.getBirthdateByFullName("John", "Doe")).thenReturn(record.getBirthdate());
        String result = medicalRecordService.getBirthdate("John", "Doe");
        assertEquals("01/01/1990", result);
    }

    @Test
    void testGetMedicalRecordByFullName_match() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        MedicalRecord result = medicalRecordService.getMedicalRecordByFullName("John", "Doe");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetMedicalRecordByFullName_noMatch() {
        when(medicalRecordRepository.findByFullName("Jane", "Smith")).thenReturn(null);

        MedicalRecord result = medicalRecordService.getMedicalRecordByFullName("Jane", "Smith");

        assertNull(result);
    }

    @Test
    void testCalculateAge_validBirthdate() {
        int age = medicalRecordService.calculateAge("01/01/1990");
        assertTrue(age > 0);
    }

    @Test
    void testCalculateAge_nullBirthdate() {
        int age = medicalRecordService.calculateAge(null);
        assertEquals(0, age);
    }

    @Test
    void testCalculateAge_blankBirthdate() {
        int age = medicalRecordService.calculateAge("");
        assertEquals(0, age);
    }

    @Test
    void testCalculateAge_invalidFormat() {
        int age = medicalRecordService.calculateAge("ad/ad");
        assertEquals(0, age);
    }

    @Test
    void testCreateMedicalRecord_newPerson() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(null);

        boolean result = medicalRecordService.addMedicalRecord(record);

        assertTrue(result);
        verify(medicalRecordRepository).createNewMedicalRecord(record);
    }

    @Test
    void testCreateMedicalRecord_duplicate() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        boolean result = medicalRecordService.addMedicalRecord(record);

        assertFalse(result);
        verify(medicalRecordRepository, never()).createNewMedicalRecord(any());
    }

    @Test
    void testUpdateMedicalRecord_existingPerson() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        MedicalRecord updated = new MedicalRecord("John", "Doe", "01/01/1990", Collections.emptyList(), Collections.emptyList());
        boolean result = medicalRecordService.updateMedicalRecord(updated);

        assertTrue(result);
        verify(medicalRecordRepository).updateMedicalRecord(updated);
    }

    @Test
    void testUpdateMedicalRecord_unknownPerson() {
        when(medicalRecordRepository.findByFullName("Jane", "Doe")).thenReturn(null);

        MedicalRecord updated = new MedicalRecord("Jane", "Doe", "01/01/1990", Collections.emptyList(), Collections.emptyList());
        boolean result = medicalRecordService.updateMedicalRecord(updated);

        assertFalse(result);
        verify(medicalRecordRepository, never()).updateMedicalRecord(any());
    }

    @Test
    void testDeleteMedicalRecord_existingPerson() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        boolean result = medicalRecordService.deleteMedicalRecord("John", "Doe");

        assertTrue(result);
        verify(medicalRecordRepository).deleteMedicalRecordByFullName("John", "Doe");
    }

    @Test
    void testDeleteMedicalRecord_unknownPerson() {
        when(medicalRecordRepository.findByFullName("Jane", "Doe")).thenReturn(null);

        boolean result = medicalRecordService.deleteMedicalRecord("Jane", "Doe");

        assertFalse(result);
        verify(medicalRecordRepository, never()).deleteMedicalRecordByFullName(anyString(), anyString());
    }
}