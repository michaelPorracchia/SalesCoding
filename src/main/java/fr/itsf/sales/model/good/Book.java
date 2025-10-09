package fr.itsf.sales.model.good;

import java.math.BigDecimal;

/**
 * Book business object
 * <p>
 * It extends the Good business object and contains the book specific properties
 */
public class Book extends Good {

    public Book(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
