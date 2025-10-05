package fr.itsf.sales.model.good;

import java.math.BigDecimal;

/**
 * Other good business object
 * <p>
 * It extends the Good business object and contains the other specific properties
 */
public class Other extends Good {

    public Other(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
