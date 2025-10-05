package fr.itsf.sales.model;

import fr.itsf.sales.model.good.Book;
import fr.itsf.sales.model.good.Good;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingBasketTest {

    @Test
    void testShoppingBasketCreation() {
        Long id = 1L;
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        List<Good> goods = Arrays.asList(book);

        ShoppingBasket basket = new ShoppingBasket(id, goods);

        assertEquals(id, basket.getId());
        assertEquals(goods, basket.getGoods());
        assertEquals(1, basket.getGoods().size());
    }

    @Test
    void testShoppingBasketCreation_withMultipleGoods() {
        Long id = 2L;
        Good book = new Book("Test Book", new BigDecimal("10.00"), 1, false);
        Good food = new fr.itsf.sales.model.good.Food("chocolate", new BigDecimal("5.00"), 2, true);
        List<Good> goods = Arrays.asList(book, food);

        ShoppingBasket basket = new ShoppingBasket(id, goods);

        assertEquals(id, basket.getId());
        assertEquals(2, basket.getGoods().size());
    }

    @Test
    void testShoppingBasketCreation_emptyBasket() {
        Long id = 3L;
        List<Good> goods = List.of();

        ShoppingBasket basket = new ShoppingBasket(id, goods);

        assertEquals(id, basket.getId());
        assertTrue(basket.getGoods().isEmpty());
    }
}