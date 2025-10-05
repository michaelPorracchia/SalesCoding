package fr.itsf.sales.model.good;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Good business object
 * <p>
 * It contains the name, the price, the imported status and the quantity of the good
 */
@Getter
@Setter
public abstract class Good {

    private final String name;
    private BigDecimal price;
    private final boolean imported;
    private final int quantity;

    /**
     * Constructor
     * @param name the name of the good
     * @param price the price of the good
     * @param quantity the quantity of the good
     * @param imported the imported status of the good
     */
    protected Good(String name, BigDecimal price, int quantity, boolean imported) {
        this.name = name;
        this.price = price;
        this.imported = imported;
        this.quantity = quantity;
    }

    /**
     * Get the unit price of the good
     * @return BigDecimal
     */
    public BigDecimal getUnitBasePrice() {
        BigDecimal qty = BigDecimal.valueOf(this.getQuantity());
        return (qty.compareTo(BigDecimal.ONE) >= 0)?
                this.getPrice().divide(qty, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }
}
