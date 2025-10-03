package fr.itsf.sales.model;

import fr.itsf.sales.model.good.Good;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingBasket {

    private final Long id;
    private final List<Good> goods;

    public ShoppingBasket(Long id, List<Good> goods) {
        this.id = id;
        this.goods = goods;
    }
}
