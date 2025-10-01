package fr.itsf.sales.model.good;

import java.math.BigDecimal;

/**
 * Food business object
 * <p>
 * It extends the Good business object and contains the food specific properties
 */
public class Food extends Good {

    public Food(String name, BigDecimal price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }
}
