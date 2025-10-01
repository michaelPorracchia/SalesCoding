package fr.itsf.sales.model.good;

import java.math.BigDecimal;

public class Book extends Good {

    public Book(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
