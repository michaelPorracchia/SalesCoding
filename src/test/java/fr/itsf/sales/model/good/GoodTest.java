
package fr.itsf.sales.model.good;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoodTest {

    @Test
    void testGetUnitBasePrice_withValidQuantity() {
        Good book = new Book("Test Book", new BigDecimal("30.00"), 3, false);

        BigDecimal unitPrice = book.getUnitBasePrice();

        assertEquals(new BigDecimal("10.00"), unitPrice);
    }

    @Test
    void testGetUnitBasePrice_withQuantityOne() {
        Good book = new Book("Test Book", new BigDecimal("15.50"), 1, false);

        BigDecimal unitPrice = book.getUnitBasePrice();

        assertEquals(new BigDecimal("15.50"), unitPrice);
    }

    @Test
    void testGetUnitBasePrice_withZeroQuantity() {
        Good book = new Book("Test Book", new BigDecimal("15.50"), 0, false);

        BigDecimal unitPrice = book.getUnitBasePrice();

        assertEquals(BigDecimal.ZERO, unitPrice);
    }

    @Test
    void testSetPrice() {
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        BigDecimal newPrice = new BigDecimal("15.00");

        book.setPrice(newPrice);

        assertEquals(newPrice, book.getPrice());
    }

    @Test
    void testGoodProperties() {
        String name = "Test Product";
        BigDecimal price = new BigDecimal("25.50");
        int quantity = 5;
        boolean imported = true;

        Good good = new Book(name, price, quantity, imported);

        assertEquals(name, good.getName());
        assertEquals(price, good.getPrice());
        assertEquals(quantity, good.getQuantity());
        assertTrue(good.isImported());
    }
}