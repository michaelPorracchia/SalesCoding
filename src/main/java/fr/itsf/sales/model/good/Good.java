package fr.itsf.sales.model.good;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Good business object
 * <p>
 * It contains the name, the price, the imported status and the quantity of the good
 */
@AllArgsConstructor
@Getter
@Setter
public abstract class Good {

    private final String name;
    private BigDecimal price;
    private final int quantity;
    private final boolean imported;

    /**
     * Get the unit price of the good
     * @return BigDecimal
     */
    public BigDecimal getUnitBasePrice() {
        if (quantity == 0) {
            return BigDecimal.ZERO;
        }
        return price.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.HALF_UP);
    }
}
