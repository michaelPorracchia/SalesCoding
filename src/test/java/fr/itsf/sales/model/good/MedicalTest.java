package fr.itsf.sales.model.good;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MedicalTest {

    @Test
    void testMedicalCreation_notImported() {
        String name = "packet of headache pills";
        BigDecimal price = new BigDecimal("9.75");
        int quantity = 1;
        boolean imported = false;

        Medical medical = new Medical(name, price, quantity, imported);

        assertEquals(name, medical.getName());
        assertEquals(price, medical.getPrice());
        assertEquals(quantity, medical.getQuantity());
        assertFalse(medical.isImported());
    }

    @Test
    void testMedicalCreation_imported() {
        String name = "imported medical supplies";
        BigDecimal price = new BigDecimal("15.00");
        int quantity = 3;
        boolean imported = true;

        Medical medical = new Medical(name, price, quantity, imported);

        assertEquals(name, medical.getName());
        assertEquals(price, medical.getPrice());
        assertEquals(quantity, medical.getQuantity());
        assertTrue(medical.isImported());
    }
}