package fr.itsf.sales.service;

import fr.itsf.sales.model.TotalTaxAndPrice;
import fr.itsf.sales.model.enums.TaxRate;
import fr.itsf.sales.model.good.Good;
import fr.itsf.sales.model.good.Other;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Tax calculation service
 * <p>
 * Apply taxe according to the good category and the good imported status
 * and round the taxe to 5 cents
 */
@Service
public class TaxService {

    private static final BigDecimal ROUND_STEP = new BigDecimal("0.05");

    private static BigDecimal roundTax(BigDecimal tax) {
        BigDecimal divided = tax.divide(new BigDecimal("0.05"), 0, RoundingMode.UP);
        return divided.multiply(ROUND_STEP);
    }

    private BigDecimal calculateTaxRateValue(Good good) {
        BigDecimal taxRatevalue = BigDecimal.ZERO;

        if (good.isImported()){
            taxRatevalue = taxRatevalue.add(TaxRate.IMPORTED.getRate());
        }
        switch (good) {
            case Other other -> taxRatevalue = taxRatevalue.add(TaxRate.STANDARD.getRate());
            default -> taxRatevalue = taxRatevalue.add(TaxRate.EXEMPTED.getRate());
        }

        return taxRatevalue;
    }

    private BigDecimal calculateUnitTaxAmount(Good good) {
        return good.getUnitBasePrice().multiply(this.calculateTaxRateValue(good));
    }

    /**
     * Calculate the total tax for a good
     * @param good the good to calculate the tax for
     * @return BigDecimal
     */
    public BigDecimal calculateGoodTotalTax(Good good) {
        BigDecimal qty = BigDecimal.valueOf(good.getQuantity());
        return qty.multiply(roundTax(this.calculateUnitTaxAmount(good)));
    }

    /**
     * Calculate the total tax amount and the included tax price for a good
     * @param good the good to calculate the tax and price for
     * @return TotalTaxAndPrice
     */
    public TotalTaxAndPrice calculateIncludedTaxPriceGood(Good good) {
        BigDecimal qty = BigDecimal.valueOf(good.getQuantity());
        TotalTaxAndPrice totalTaxAndPrice = new TotalTaxAndPrice(BigDecimal.ZERO, BigDecimal.ZERO);

        totalTaxAndPrice.setTaxAmount(calculateGoodTotalTax(good));
        totalTaxAndPrice.setIncludedTaxPriceGood(qty.multiply(good.getUnitBasePrice()).add(totalTaxAndPrice.getTaxAmount()));

        return totalTaxAndPrice;
    }

}
