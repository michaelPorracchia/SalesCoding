package fr.itsf.sales.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Receipt {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    private Long id;
    private ShoppingBasket shoppingBasket;
    private BigDecimal salesTaxes;
    private BigDecimal total;

    public Receipt(ShoppingBasket shoppingBasket) {
        this.id = COUNTER.getAndIncrement();
        this.shoppingBasket = shoppingBasket;
    }
}
