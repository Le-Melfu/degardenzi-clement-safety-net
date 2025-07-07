package com.safetynet.repository.implementations;

import com.safetynet.config.loader.FakeDatabase;
import com.safetynet.model.FirestationMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirestationInMemoryRepositoryTest {

    private FakeDatabase fakeDatabase;
    private FirestationInMemoryRepository firestationRepository;

    @BeforeEach
    public void setUp() {
        fakeDatabase = new FakeDatabase();
        firestationRepository = new FirestationInMemoryRepository(fakeDatabase);

        fakeDatabase.setFirestationData(List.of(
                new FirestationMapping("1509 Culver St", "1"),
                new FirestationMapping("29 15th St", "2")
        ));
    }

    @Test
    public void testFindAll() {
        List<FirestationMapping> all = firestationRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testFindByAddress_found() {
        FirestationMapping result = firestationRepository.findByAddress("1509 Culver St");
        assertNotNull(result);
        assertEquals("1", result.getStation());
    }

    @Test
    public void testFindByAddress_notFound() {
        FirestationMapping result = firestationRepository.findByAddress("Unknown St");
        assertNull(result);
    }

    @Test
    public void testGetStationAddresses_found() {
        List<String> addresses = firestationRepository.getStationAdresses("1");
        assertEquals(List.of("1509 Culver St"), addresses);
    }

    @Test
    public void testGetStationAddresses_notFound() {
        List<String> addresses = firestationRepository.getStationAdresses("99");
        assertTrue(addresses.isEmpty());
    }

    @Test
    public void testCreateNewFirestationMapping_success() {
        firestationRepository.createNewFirestationMapping(new FirestationMapping("123 New St", "3"));
        assertEquals(3, fakeDatabase.getFirestationMappings().size());
    }

    @Test
    public void testCreateNewFirestationMapping_duplicate() {
        firestationRepository.createNewFirestationMapping(new FirestationMapping("1509 Culver St", "1"));
        assertEquals(2, fakeDatabase.getFirestationMappings().size());
    }

    @Test
    public void testUpdateFirestationMapping_found() {
        firestationRepository.updateFirestationMapping(new FirestationMapping("29 15th St", "9"));
        FirestationMapping updated = firestationRepository.findByAddress("29 15th St");
        assertEquals("9", updated.getStation());
    }

    @Test
    public void testUpdateFirestationMapping_notFound() {
        firestationRepository.updateFirestationMapping(new FirestationMapping("404 Ghost St", "4"));
        assertEquals(2, fakeDatabase.getFirestationMappings().size());
    }

    @Test
    public void testDeleteFirestationMappingByAddress_found() {
        firestationRepository.deleteFirestationMappingByAddress("1509 Culver St");
        assertNull(firestationRepository.findByAddress("1509 Culver St"));
    }

    @Test
    public void testDeleteFirestationMappingByAddress_notFound() {
        firestationRepository.deleteFirestationMappingByAddress("404 Ghost St");
        assertEquals(2, fakeDatabase.getFirestationMappings().size());
    }
}
