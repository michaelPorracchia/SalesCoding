package fr.itsf.sales.model.good;

import java.math.BigDecimal;

public class Other extends Good {

    public Other(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
