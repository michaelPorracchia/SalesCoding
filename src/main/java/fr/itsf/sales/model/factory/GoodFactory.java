package fr.itsf.sales.model.factory;

import fr.itsf.model.GoodSpec;
import fr.itsf.sales.model.good.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Factory for Good business object creation based on the GoodSpec category
 * <p>
 * @see GoodSpec.CategoryEnum
 */
@Component
public class GoodFactory {
    public Good createGood(GoodSpec.CategoryEnum category, String name, BigDecimal price, int quantity, boolean imported){
        return switch (category) {
            case BOOK -> new Book(name, price, quantity, imported);
            case FOOD -> new Food(name, price, quantity, imported);
            case MEDICAL -> new Medical(name, price, quantity, imported);
            case OTHER -> new Other(name, price, quantity, imported);
        };
    }
}
