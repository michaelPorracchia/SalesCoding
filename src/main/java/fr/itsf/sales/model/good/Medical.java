package fr.itsf.sales.model.good;

import java.math.BigDecimal;

/**
 * Medical business object
 * <p>
 * It extends the Good business object and contains the medical specific properties
 */
public class Medical extends Good {

    public Medical(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
