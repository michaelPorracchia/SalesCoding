package fr.itsf.sales.model.good;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testFoodCreation_notImported() {
        String name = "chocolate bar";
        BigDecimal price = new BigDecimal("0.85");
        int quantity = 1;
        boolean imported = false;

        Food food = new Food(name, price, quantity, imported);

        assertEquals(name, food.getName());
        assertEquals(price, food.getPrice());
        assertEquals(quantity, food.getQuantity());
        assertFalse(food.isImported());
    }

    @Test
    void testFoodCreation_imported() {
        String name = "imported box of chocolates";
        BigDecimal price = new BigDecimal("11.25");
        int quantity = 1;
        boolean imported = true;

        Food food = new Food(name, price, quantity, imported);

        assertEquals(name, food.getName());
        assertEquals(price, food.getPrice());
        assertEquals(quantity, food.getQuantity());
        assertTrue(food.isImported());
    }
}