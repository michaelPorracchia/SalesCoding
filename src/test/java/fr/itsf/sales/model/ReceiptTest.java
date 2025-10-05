package fr.itsf.sales.model;

import fr.itsf.sales.model.good.Book;
import fr.itsf.sales.model.good.Good;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void testReceiptCreation() {
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        ShoppingBasket basket = new ShoppingBasket(1L, List.of(book));

        Receipt receipt = new Receipt(basket);

        assertNotNull(receipt.getId());
        assertEquals(basket, receipt.getShoppingBasket());
        assertNull(receipt.getSalesTaxes());
        assertNull(receipt.getTotal());
    }

    @Test
    void testReceiptSetters() {
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        ShoppingBasket basket = new ShoppingBasket(1L, List.of(book));
        Receipt receipt = new Receipt(basket);
        BigDecimal taxes = new BigDecimal("1.00");
        BigDecimal total = new BigDecimal("11.00");

        receipt.setSalesTaxes(taxes);
        receipt.setTotal(total);

        assertEquals(taxes, receipt.getSalesTaxes());
        assertEquals(total, receipt.getTotal());
    }

    @Test
    void testReceiptIdIncrement() {
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        ShoppingBasket basket = new ShoppingBasket(1L, List.of(book));

        Receipt receipt1 = new Receipt(basket);
        Receipt receipt2 = new Receipt(basket);

        assertNotNull(receipt1.getId());
        assertNotNull(receipt2.getId());
        assertTrue(receipt2.getId() > receipt1.getId());
    }
}