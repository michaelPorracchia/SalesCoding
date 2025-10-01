package fr.itsf.sales.model.good;

import java.math.BigDecimal;

public class Food extends Good {

    public Food(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
