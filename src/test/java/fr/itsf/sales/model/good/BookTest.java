package fr.itsf.sales.model.good;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation_notImported() {
        String name = "Java Programming";
        BigDecimal price = new BigDecimal("12.49");
        int quantity = 1;
        boolean imported = false;

        Book book = new Book(name, price, quantity, imported);

        assertEquals(name, book.getName());
        assertEquals(price, book.getPrice());
        assertEquals(quantity, book.getQuantity());
        assertFalse(book.isImported());
    }

    @Test
    void testBookCreation_imported() {
        String name = "Imported Book";
        BigDecimal price = new BigDecimal("20.00");
        int quantity = 2;
        boolean imported = true;

        Book book = new Book(name, price, quantity, imported);

        assertEquals(name, book.getName());
        assertEquals(price, book.getPrice());
        assertEquals(quantity, book.getQuantity());
        assertTrue(book.isImported());
    }
}