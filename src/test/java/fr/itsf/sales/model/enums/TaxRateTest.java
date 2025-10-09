package fr.itsf.sales.model.enums;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaxRateTest {

    @Test
    void testStandardTaxRate() {
        TaxRate taxRate = TaxRate.STANDARD;

        assertEquals(new BigDecimal("0.10"), taxRate.getRate());
    }

    @Test
    void testImportedTaxRate() {
        TaxRate taxRate = TaxRate.IMPORTED;

        assertEquals(new BigDecimal("0.05"), taxRate.getRate());
    }

    @Test
    void testExemptedTaxRate() {
        TaxRate taxRate = TaxRate.EXEMPTED;

        assertEquals(BigDecimal.ZERO, taxRate.getRate());
    }

    @Test
    void testAllTaxRatesExist() {
        assertNotNull(TaxRate.STANDARD);
        assertNotNull(TaxRate.IMPORTED);
        assertNotNull(TaxRate.EXEMPTED);
        assertEquals(3, TaxRate.values().length);
    }
}