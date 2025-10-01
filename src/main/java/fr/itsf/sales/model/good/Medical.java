package fr.itsf.sales.model.good;

import java.math.BigDecimal;

public class Medical extends Good {

    public Medical(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
