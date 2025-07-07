package com.safetynet.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordTest {

    @Test
    void testEqualsAndHashCode() {
        MedicalRecord r1 = new MedicalRecord("John", "Doe", "01/01/1990",
                List.of("med1:100mg"), List.of("peanut"));
        MedicalRecord r2 = new MedicalRecord("John", "Doe", "01/01/1990",
                List.of("med1:100mg"), List.of("peanut"));
        MedicalRecord r3 = new MedicalRecord("Jane", "Doe", "02/02/1985",
                List.of("med2:200mg"), List.of("gluten"));

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
    }

    @Test
    void testToString() {
        MedicalRecord r = new MedicalRecord("Alice", "Smith", "03/03/2000",
                List.of("medX"), List.of("dust"));
        String result = r.toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("dust"));
    }
}
