package fr.itsf.sales.model;

import fr.itsf.sales.model.good.Good;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Shopping basket business object
 * <p>
 * It contains the goods of the basket and the basket id
 */
@Getter
@Setter
public class ShoppingBasket {

    private final Long id;
    private final List<Good> goods;

    /**
     * Constructor
     * @param id the basket id
     * @param goods the goods of the basket
     */
    public ShoppingBasket(Long id, List<Good> goods) {
        this.id = id;
        this.goods = goods;
    }
}
