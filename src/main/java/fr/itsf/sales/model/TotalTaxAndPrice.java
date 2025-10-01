package fr.itsf.sales.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Total tax and included tax price of a good
 * <p>
 * It is a combination of the total tax amount and the included tax price of the good
 */
@Getter
@Setter
public class TotalTaxAndPrice {

    private BigDecimal includedTaxPriceGood;
    private BigDecimal taxAmount;

    /**
     * Constructor
     * @param taxAmount the total tax amount
     * @param basePrice the included tax price of the good
     */
    public TotalTaxAndPrice(BigDecimal taxAmount, BigDecimal basePrice) {
        this.taxAmount = taxAmount;
        this.includedTaxPriceGood = taxAmount.add(basePrice);
    }

    /**
     * Copy constructor
     * @param totalTaxAndPrice the TotalTaxAndPrice to copy from
     */
    public TotalTaxAndPrice(TotalTaxAndPrice totalTaxAndPrice) {
        this.taxAmount = totalTaxAndPrice.taxAmount;
        this.includedTaxPriceGood = totalTaxAndPrice.includedTaxPriceGood;
    }
}
