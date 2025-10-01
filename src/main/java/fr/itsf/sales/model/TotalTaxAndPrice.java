package fr.itsf.sales.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TotalTaxAndPrice {

    private BigDecimal includedTaxPriceGood;
    private BigDecimal taxAmount;

    public TotalTaxAndPrice(BigDecimal taxAmount, BigDecimal basePrice) {
        this.taxAmount = taxAmount;
        this.includedTaxPriceGood = taxAmount.add(basePrice);
    }

    public TotalTaxAndPrice(TotalTaxAndPrice totalTaxAndPrice) {
        this.taxAmount = totalTaxAndPrice.taxAmount;
        this.includedTaxPriceGood = totalTaxAndPrice.includedTaxPriceGood;
    }
}
