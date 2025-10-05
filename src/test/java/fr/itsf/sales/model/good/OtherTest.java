package fr.itsf.sales.model.good;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OtherTest {

    @Test
    void testOtherCreation_notImported() {
        String name = "music CD";
        BigDecimal price = new BigDecimal("14.99");
        int quantity = 1;
        boolean imported = false;

        Other other = new Other(name, price, quantity, imported);

        assertEquals(name, other.getName());
        assertEquals(price, other.getPrice());
        assertEquals(quantity, other.getQuantity());
        assertFalse(other.isImported());
    }

    @Test
    void testOtherCreation_imported() {
        String name = "imported bottle of perfume";
        BigDecimal price = new BigDecimal("47.50");
        int quantity = 1;
        boolean imported = true;

        Other other = new Other(name, price, quantity, imported);

        assertEquals(name, other.getName());
        assertEquals(price, other.getPrice());
        assertEquals(quantity, other.getQuantity());
        assertTrue(other.isImported());
    }
}