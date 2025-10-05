package fr.itsf.sales.model.factory;

import fr.itsf.model.GoodSpec;
import fr.itsf.sales.model.good.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GoodFactoryTest {

    private GoodFactory goodFactory;

    @BeforeEach
    void setUp() {
        goodFactory = new GoodFactory();
    }

    @Test
    void testCreateBook() {
        String name = "Test Book";
        BigDecimal price = new BigDecimal("12.49");
        int quantity = 1;
        boolean imported = false;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.BOOK, name, price, quantity, imported);

        assertInstanceOf(Book.class, good);
        assertEquals(name, good.getName());
        assertEquals(price, good.getPrice());
        assertEquals(quantity, good.getQuantity());
        assertFalse(good.isImported());
    }

    @Test
    void testCreateFood() {
        String name = "chocolate bar";
        BigDecimal price = new BigDecimal("0.85");
        int quantity = 1;
        boolean imported = false;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.FOOD, name, price, quantity, imported);

        assertInstanceOf(Food.class, good);
        assertEquals(name, good.getName());
        assertEquals(price, good.getPrice());
        assertEquals(quantity, good.getQuantity());
        assertFalse(good.isImported());
    }

    @Test
    void testCreateMedical() {
        String name = "packet of headache pills";
        BigDecimal price = new BigDecimal("9.75");
        int quantity = 1;
        boolean imported = false;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.MEDICAL, name, price, quantity, imported);

        assertInstanceOf(Medical.class, good);
        assertEquals(name, good.getName());
        assertEquals(price, good.getPrice());
        assertEquals(quantity, good.getQuantity());
        assertFalse(good.isImported());
    }

    @Test
    void testCreateOther() {
        String name = "music CD";
        BigDecimal price = new BigDecimal("14.99");
        int quantity = 1;
        boolean imported = false;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.OTHER, name, price, quantity, imported);

        assertInstanceOf(Other.class, good);
        assertEquals(name, good.getName());
        assertEquals(price, good.getPrice());
        assertEquals(quantity, good.getQuantity());
        assertFalse(good.isImported());
    }

    @Test
    void testCreateImportedBook() {
        String name = "imported book";
        BigDecimal price = new BigDecimal("24.99");
        int quantity = 2;
        boolean imported = true;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.BOOK, name, price, quantity, imported);

        assertInstanceOf(Book.class, good);
        assertTrue(good.isImported());
    }

    @Test
    void testCreateImportedFood() {
        String name = "imported box of chocolates";
        BigDecimal price = new BigDecimal("11.25");
        int quantity = 1;
        boolean imported = true;

        Good good = goodFactory.createGood(GoodSpec.CategoryEnum.FOOD, name, price, quantity, imported);

        assertInstanceOf(Food.class, good);
        assertTrue(good.isImported());
    }
}