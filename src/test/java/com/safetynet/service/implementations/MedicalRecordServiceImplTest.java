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
    void testGetMedicalRecordByFullName() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        MedicalRecord result = medicalRecordService.getMedicalRecordByFullName("John", "Doe");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetMedicalRecordByFullNameNoMatch() {
        when(medicalRecordRepository.findByFullName("Jane", "Smith")).thenReturn(null);

        MedicalRecord result = medicalRecordService.getMedicalRecordByFullName("Jane", "Smith");

        assertNull(result);
    }

    @Test
    void testCalculateAgeValidBirthdate() {
        int age = medicalRecordService.calculateAge("01/01/1990", "test", "test");
        assertTrue(age > 0);
    }

    @Test
    void testCalculateAgeNullBirthdate() {
        int age = medicalRecordService.calculateAge(null, "test", "test");
        assertEquals(0, age);
    }

    @Test
    void testCalculateAgeBlankBirthdate() {
        int age = medicalRecordService.calculateAge("", "test", "test");
        assertEquals(0, age);
    }

    @Test
    void testCalculateAgeInvalidFormat() {
        int age = medicalRecordService.calculateAge("ad/ad", "test", "test");
        assertEquals(0, age);
    }

    @Test
    void testCreateMedicalRecord() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(null);

        boolean result = medicalRecordService.addMedicalRecord(record);

        assertTrue(result);
        verify(medicalRecordRepository).createNewMedicalRecord(record);
    }

    @Test
    void testCreateMedicalRecordDuplicate() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        boolean result = medicalRecordService.addMedicalRecord(record);

        assertFalse(result);
        verify(medicalRecordRepository, never()).createNewMedicalRecord(any());
    }

    @Test
    void testUpdateMedicalRecord() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        MedicalRecord updated = new MedicalRecord("John", "Doe", "01/01/1990", Collections.emptyList(), Collections.emptyList());
        boolean result = medicalRecordService.updateMedicalRecord(updated);

        assertTrue(result);
        verify(medicalRecordRepository).updateMedicalRecord(updated);
    }

    @Test
    void testUpdateMedicalRecordNotFound() {
        when(medicalRecordRepository.findByFullName("Jane", "Doe")).thenReturn(null);

        MedicalRecord updated = new MedicalRecord("Jane", "Doe", "01/01/1990", Collections.emptyList(), Collections.emptyList());
        boolean result = medicalRecordService.updateMedicalRecord(updated);

        assertFalse(result);
        verify(medicalRecordRepository, never()).updateMedicalRecord(any());
    }

    @Test
    void testDeleteMedicalRecord() {
        when(medicalRecordRepository.findByFullName("John", "Doe")).thenReturn(record);

        boolean result = medicalRecordService.deleteMedicalRecord("John", "Doe");

        assertTrue(result);
        verify(medicalRecordRepository).deleteMedicalRecordByFullName("John", "Doe");
    }

    @Test
    void testDeleteMedicalRecordNotFound() {
        when(medicalRecordRepository.findByFullName("Jane", "Doe")).thenReturn(null);

        boolean result = medicalRecordService.deleteMedicalRecord("Jane", "Doe");

        assertFalse(result);
        verify(medicalRecordRepository, never()).deleteMedicalRecordByFullName(anyString(), anyString());
    }
}