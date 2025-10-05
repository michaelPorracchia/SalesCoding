package fr.itsf.sales.service;

import fr.itsf.sales.model.TotalTaxAndPrice;
import fr.itsf.sales.model.good.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxServiceTest {

    private TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new TaxService();
    }

    @Test
    void testCalculateGoodTotalTax_book_notImported() {
        Good book = new Book("book", new BigDecimal("12.49"), 1, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(book);

        assertEquals(new BigDecimal("0.00"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_book_imported() {
        Good book = new Book("imported book", new BigDecimal("24.00"), 1, true);

        BigDecimal tax = taxService.calculateGoodTotalTax(book);

        assertEquals(new BigDecimal("1.20"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_food_notImported() {
        Good food = new Food("chocolate bar", new BigDecimal("0.85"), 1, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(food);

        assertEquals(new BigDecimal("0.00"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_food_imported() {
        Good food = new Food("imported box of chocolates", new BigDecimal("10.00"), 1, true);

        BigDecimal tax = taxService.calculateGoodTotalTax(food);

        assertEquals(new BigDecimal("0.50"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_medical_notImported() {
        Good medical = new Medical("packet of headache pills", new BigDecimal("9.75"), 1, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(medical);

        assertEquals(new BigDecimal("0.00"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_medical_imported() {
        Good medical = new Medical("imported medical pills", new BigDecimal("20.00"), 1, true);

        BigDecimal tax = taxService.calculateGoodTotalTax(medical);

        assertEquals(new BigDecimal("1.00"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_other_notImported() {
        Good other = new Other("music CD", new BigDecimal("14.99"), 1, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(other);

        assertEquals(new BigDecimal("1.50"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_other_imported() {
        Good other = new Other("imported bottle of perfume", new BigDecimal("27.99"), 1, true);

        BigDecimal tax = taxService.calculateGoodTotalTax(other);

        assertEquals(new BigDecimal("4.20"), tax);
    }

    @Test
    void testCalculateGoodTotalTax_withMultipleQuantity() {
        Good other = new Other("music CD", new BigDecimal("29.98"), 2, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(other);

        assertEquals(new BigDecimal("3.00"), tax);
    }

    @Test
    void testCalculateIncludedTaxPriceGood_book() {
        Good book = new Book("book", new BigDecimal("12.49"), 1, false);

        TotalTaxAndPrice result = taxService.calculateIncludedTaxPriceGood(book);

        assertEquals(new BigDecimal("0.00"), result.getTaxAmount());
        assertEquals(new BigDecimal("12.49"), result.getIncludedTaxPriceGood());
    }

    @Test
    void testCalculateIncludedTaxPriceGood_importedFood() {
        Good food = new Food("imported box of chocolates", new BigDecimal("10.00"), 1, true);

        TotalTaxAndPrice result = taxService.calculateIncludedTaxPriceGood(food);

        assertEquals(new BigDecimal("0.50"), result.getTaxAmount());
        assertEquals(new BigDecimal("10.50"), result.getIncludedTaxPriceGood());
    }

    @Test
    void testCalculateIncludedTaxPriceGood_importedPerfume() {
        Good other = new Other("imported bottle of perfume", new BigDecimal("47.50"), 1, true);

        TotalTaxAndPrice result = taxService.calculateIncludedTaxPriceGood(other);

        assertEquals(new BigDecimal("7.15"), result.getTaxAmount());
        assertEquals(new BigDecimal("54.65"), result.getIncludedTaxPriceGood());
    }

    @Test
    void testCalculateIncludedTaxPriceGood_musicCD() {
        Good other = new Other("music CD", new BigDecimal("14.99"), 1, false);
        TotalTaxAndPrice result = taxService.calculateIncludedTaxPriceGood(other);

        assertEquals(new BigDecimal("1.50"), result.getTaxAmount());
        assertEquals(new BigDecimal("16.49"), result.getIncludedTaxPriceGood());
    }

    @Test
    void testTaxRounding_roundsUp() {
        //A rounding up required price
        Good other = new Other("product", new BigDecimal("10.11"), 1, false);

        BigDecimal tax = taxService.calculateGoodTotalTax(other);

        assertEquals(new BigDecimal("1.05"), tax);
    }

    @Test
    void testCalculateIncludedTaxPriceGood_withZeroQuantity() {
        Good book = new Book("book", new BigDecimal("12.49"), 0, false);

        TotalTaxAndPrice result = taxService.calculateIncludedTaxPriceGood(book);

        assertEquals(new BigDecimal("0.00"), result.getTaxAmount());
        assertEquals(new BigDecimal("0.00"), result.getIncludedTaxPriceGood());
    }
}