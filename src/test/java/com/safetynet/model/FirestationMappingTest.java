package com.safetynet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirestationMappingTest {

    @Test
    void testEqualsAndHashCode() {
        FirestationMapping f1 = new FirestationMapping("123 Street", "1");
        FirestationMapping f2 = new FirestationMapping("123 Street", "1");
        FirestationMapping f3 = new FirestationMapping("456 Avenue", "2");

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1, f3);
    }

    @Test
    void testToString() {
        FirestationMapping f = new FirestationMapping("789 Boulevard", "3");
        String result = f.toString();
        assertTrue(result.contains("789 Boulevard"));
        assertTrue(result.contains("3"));
    }
}
