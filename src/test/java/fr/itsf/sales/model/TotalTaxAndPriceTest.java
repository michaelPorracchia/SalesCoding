package fr.itsf.sales.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalTaxAndPriceTest {

    @Test
    void testTotalTaxAndPriceCreation() {
        BigDecimal taxAmount = new BigDecimal("1.50");
        BigDecimal basePrice = new BigDecimal("10.00");

        TotalTaxAndPrice totalTaxAndPrice = new TotalTaxAndPrice(taxAmount, basePrice);

        assertEquals(taxAmount, totalTaxAndPrice.getTaxAmount());
        assertEquals(new BigDecimal("11.50"), totalTaxAndPrice.getIncludedTaxPriceGood());
    }

    @Test
    void testTotalTaxAndPriceCopyConstructor() {
        BigDecimal taxAmount = new BigDecimal("2.00");
        BigDecimal basePrice = new BigDecimal("20.00");
        TotalTaxAndPrice original = new TotalTaxAndPrice(taxAmount, basePrice);

        TotalTaxAndPrice copy = new TotalTaxAndPrice(original);

        assertEquals(original.getTaxAmount(), copy.getTaxAmount());
        assertEquals(original.getIncludedTaxPriceGood(), copy.getIncludedTaxPriceGood());
    }

    @Test
    void testSetters() {
        TotalTaxAndPrice totalTaxAndPrice = new TotalTaxAndPrice(BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newTax = new BigDecimal("5.00");
        BigDecimal newPrice = new BigDecimal("50.00");

        totalTaxAndPrice.setTaxAmount(newTax);
        totalTaxAndPrice.setIncludedTaxPriceGood(newPrice);

        assertEquals(newTax, totalTaxAndPrice.getTaxAmount());
        assertEquals(newPrice, totalTaxAndPrice.getIncludedTaxPriceGood());
    }

    @Test
    void testTotalTaxAndPriceWithZeroValues() {
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;

        TotalTaxAndPrice totalTaxAndPrice = new TotalTaxAndPrice(taxAmount, basePrice);

        assertEquals(BigDecimal.ZERO, totalTaxAndPrice.getTaxAmount());
        assertEquals(BigDecimal.ZERO, totalTaxAndPrice.getIncludedTaxPriceGood());
    }
}