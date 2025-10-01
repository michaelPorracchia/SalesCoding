package fr.itsf.sales.model.good;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public abstract class Good {

    private final String name;
    private BigDecimal price;
    private final boolean imported;
    private final int quantity;

    public Good(String name, BigDecimal price, int quantity, boolean imported) {
        this.name = name;
        this.price = price;
        this.imported = imported;
        this.quantity = quantity;
    }

    public BigDecimal getUnitBasePrice() {
        BigDecimal qty = BigDecimal.valueOf(this.getQuantity());
        return (qty.compareTo(BigDecimal.ONE) >= 0)?
                this.getPrice().divide(qty, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }
}
