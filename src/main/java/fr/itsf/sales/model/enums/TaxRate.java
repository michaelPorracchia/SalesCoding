package fr.itsf.sales.model.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TaxRate {
    STANDARD(new BigDecimal("0.10")),
    IMPORTED(new BigDecimal("0.05")),
    EXEMPTED(BigDecimal.ZERO);

    private final BigDecimal rate;

    TaxRate(BigDecimal rate) {
        this.rate = rate;
    }
}
