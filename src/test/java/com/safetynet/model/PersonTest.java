package com.safetynet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testEqualsAndHashCode() {
        Person p1 = new Person("John", "Doe", "1509 Culver St", "Culver", "97451", "555-5555", "john@example.com");
        Person p2 = new Person("John", "Doe", "1509 Culver St", "Culver", "97451", "555-5555", "john@example.com");
        Person p3 = new Person("Jane", "Doe", "29 15th St", "Culver", "97451", "555-6666", "jane@example.com");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }

    @Test
    void testToString() {
        Person p = new Person("Alice", "Smith", "123 Main St", "Springfield", "12345", "000-1111", "alice@mail.com");
        String result = p.toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Smith"));
    }

    @Test
    void testGetZip() {
        Person p = new Person("Bob", "Smith", "456 Oak St", "Townsville", "99999", "888-0000", "bob@mail.com");
        assertEquals("99999", p.getZip());
    }
}
