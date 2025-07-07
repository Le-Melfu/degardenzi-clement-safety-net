package com.safetynet.service.implementations;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.Person;
import com.safetynet.model.dto.FirestationCoverageDTO;
import com.safetynet.repository.interfaces.FirestationRepository;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationServiceImplTest {

    @Mock
    private FirestationRepository firestationRepository;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FirestationServiceImpl firestationService;

    private FirestationMapping mapping;
    private Person person1;
    private Person person2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapping = new FirestationMapping("1509 Culver St", "3");
        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "123-456-7890", "john@doe.com");
        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "123-456-7891", "jacob@doe.com");
    }

    @Test
    void testGetFirestationByAddress_found() {
        when(firestationRepository.findByAddress("1509 Culver St")).thenReturn(mapping);
        FirestationMapping result = firestationService.getFirestationByAddress("1509 Culver St");
        assertNotNull(result);
        assertEquals("3", result.getStation());
    }

    @Test
    void testGetStationAddresses_found() {
        List<String> addresses = List.of("1509 Culver St", "29 15th St");
        when(firestationRepository.getStationAdresses("3")).thenReturn(addresses);
        List<String> result = firestationService.getStationAdresses("3");
        assertEquals(2, result.size());
        assertTrue(result.contains("1509 Culver St"));
    }

    @Test
    void testGetPersonsCoveredByStation() {
        List<String> addresses = List.of("1509 Culver St");
        List<Person> persons = List.of(person1, person2);
        when(firestationRepository.getStationAdresses("3")).thenReturn(addresses);
        when(personService.getPersonsByAddress("1509 Culver St")).thenReturn(persons);
        when(medicalRecordService.getBirthdate("John", "Boyd")).thenReturn("03/06/1984");
        when(medicalRecordService.getBirthdate("Jacob", "Boyd")).thenReturn("03/06/2012");
        when(medicalRecordService.calculateAge("03/06/1984", "John", "Boyd")).thenReturn(40);
        when(medicalRecordService.calculateAge("03/06/2012", "Jacob", "Boyd")).thenReturn(12);

        FirestationCoverageDTO result = firestationService.getPersonsCoveredByStation("3");

        assertEquals(2, result.persons().size());
        assertEquals(1, result.adultCount());
        assertEquals(1, result.childCount());
    }

    @Test
    void testCreateNewFirestation_new() {
        when(firestationRepository.findAll()).thenReturn(Collections.emptyList());
        boolean result = firestationService.createNewFirestation(mapping);
        assertTrue(result);
        verify(firestationRepository).createNewFirestationMapping(mapping);
    }

    @Test
    void testCreateNewFirestation_duplicate() {
        when(firestationRepository.findAll()).thenReturn(List.of(mapping));
        boolean result = firestationService.createNewFirestation(mapping);
        assertFalse(result);
        verify(firestationRepository, never()).createNewFirestationMapping(any());
    }

    @Test
    void testUpdateFirestation_existing() {
        when(firestationRepository.findByAddress("1509 Culver St")).thenReturn(mapping);
        FirestationMapping updated = new FirestationMapping("1509 Culver St", "4");
        boolean result = firestationService.updateFirestation(updated);
        assertTrue(result);
        verify(firestationRepository).updateFirestationMapping(updated);
    }

    @Test
    void testUpdateFirestation_notFound() {
        when(firestationRepository.findByAddress("Unknown Address")).thenReturn(null);
        FirestationMapping updated = new FirestationMapping("Unknown Address", "4");
        boolean result = firestationService.updateFirestation(updated);
        assertFalse(result);
        verify(firestationRepository, never()).updateFirestationMapping(any());
    }

    @Test
    void testDeleteFirestationByAddress_existing() {
        when(firestationRepository.findByAddress("1509 Culver St")).thenReturn(mapping);
        boolean result = firestationService.deleteFirestationByAddress("1509 Culver St");
        assertTrue(result);
        verify(firestationRepository).deleteFirestationMappingByAddress("1509 Culver St");
    }

    @Test
    void testDeleteFirestationByAddress_notFound() {
        when(firestationRepository.findByAddress("Unknown Address")).thenReturn(null);
        boolean result = firestationService.deleteFirestationByAddress("Unknown Address");
        assertFalse(result);
        verify(firestationRepository, never()).deleteFirestationMappingByAddress(anyString());
    }
}
