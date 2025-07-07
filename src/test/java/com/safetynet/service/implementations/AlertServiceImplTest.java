package com.safetynet.service.implementations;

import com.safetynet.model.FirestationMapping;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.model.dto.ChildAlertDTO;
import com.safetynet.model.dto.FireIncidentDTO;
import com.safetynet.model.dto.PersonWithMedicalDataDTO;
import com.safetynet.model.dto.StationFloodCoverageDTO;
import com.safetynet.service.interfaces.FirestationService;
import com.safetynet.service.interfaces.MedicalRecordService;
import com.safetynet.service.interfaces.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AlertServiceImplTest {

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private AlertServiceImpl alertService;

    private Person child;
    private Person adult;
    private MedicalRecord childRecord;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        child = new Person("Tim", "Doe", "123 Street", "City", "75000", "1234", "tim@doe.com");
        adult = new Person("John", "Doe", "123 Street", "City", "75000", "5678", "john@doe.com");

        childRecord = new MedicalRecord("Tim", "Doe", "2015-01-01", List.of("med1"), List.of("allergy1"));
    }

    @Test
    public void testGetChildrenByAddress() {
        when(personService.getPersonsByAddress("123 Street")).thenReturn(List.of(child, adult));
        when(medicalRecordService.getBirthdate("Tim", "Doe")).thenReturn("2015/01/01");
        when(medicalRecordService.getBirthdate("John", "Doe")).thenReturn("1980/01/01");
        when(medicalRecordService.calculateAge("2015/01/01", "Tim", "Doe")).thenReturn(9);
        when(medicalRecordService.calculateAge("1980/01/01", "John", "Doe")).thenReturn(44);

        ChildAlertDTO result = alertService.getChildrenByAddress("123 Street");

        assertEquals(1, result.children().size());
        assertEquals(1, result.otherMembers().size());
    }

    @Test
    public void testGetPhoneNumbersByStation() {
        when(firestationService.getStationAdresses("1")).thenReturn(List.of("123 Street"));
        when(personService.getPersonsByAddress("123 Street")).thenReturn(List.of(child, adult));

        List<String> result = alertService.getPhoneNumbersByStation("1");

        assertEquals(2, result.size());
        assertTrue(result.contains("1234"));
        assertTrue(result.contains("5678"));
    }

    @Test
    public void testGetFireIncidentByAddress() {
        when(firestationService.getFirestationByAddress("123 Street")).thenReturn(new FirestationMapping("123 Street", "2"));
        when(personService.getPersonsByAddress("123 Street")).thenReturn(List.of(child));
        when(medicalRecordService.getMedicalRecordByFullName("Tim", "Doe")).thenReturn(childRecord);
        when(medicalRecordService.calculateAge("2015-01-01", "test", "test")).thenReturn(9);

        FireIncidentDTO result = alertService.getFireIncidentByAddress("123 Street");

        assertNotNull(result);
        assertEquals("2", result.stationNumber());
        assertEquals(1, result.residents().size());
    }

    @Test
    public void testGetFireIncidentByAddress_noStation() {
        when(firestationService.getFirestationByAddress("123 Street")).thenReturn(null);
        FireIncidentDTO result = alertService.getFireIncidentByAddress("123 Street");

        assertNull(result);
    }


    @Test
    public void testGetStationsFloodCoverage() {
        when(firestationService.getStationAdresses("1")).thenReturn(List.of("123 Street"));
        when(personService.getPersonsByAddress("123 Street")).thenReturn(List.of(child));
        when(medicalRecordService.getMedicalRecordByFullName("Tim", "Doe")).thenReturn(childRecord);
        when(medicalRecordService.calculateAge("2015-01-01", "test", "test")).thenReturn(9);

        List<StationFloodCoverageDTO> result = alertService.getStationsFloodCoverage(List.of("1"));

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).stationNumber());
        assertEquals(1, result.get(0).households().size());
    }

    @Test
    public void testGetPersonsInfosByLastName() {
        when(personService.getAllPersons()).thenReturn(List.of(child));
        when(medicalRecordService.getMedicalRecordByFullName("Tim", "Doe")).thenReturn(childRecord);
        when(medicalRecordService.calculateAge("2015-01-01", "test", "test")).thenReturn(9);

        List<PersonWithMedicalDataDTO> result = alertService.getPersonsInfosByLastName("Doe");

        assertEquals(1, result.size());
        assertEquals("Tim", result.get(0).firstName());
    }

    @Test
    public void testGetEmailsByCity() {
        when(personService.getAllPersons()).thenReturn(List.of(child, adult));

        List<String> result = alertService.getEmailsByCity("City");

        assertEquals(2, result.size());
        assertTrue(result.contains("tim@doe.com"));
        assertTrue(result.contains("john@doe.com"));
    }
}
