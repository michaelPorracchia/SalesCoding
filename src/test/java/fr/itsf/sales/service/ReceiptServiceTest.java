package fr.itsf.sales.service;

import fr.itsf.model.GoodSpec;
import fr.itsf.model.ReceiptSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.model.Receipt;
import fr.itsf.sales.model.ShoppingBasket;
import fr.itsf.sales.model.TotalTaxAndPrice;
import fr.itsf.sales.model.factory.GoodFactory;
import fr.itsf.sales.model.good.Book;
import fr.itsf.sales.model.good.Food;
import fr.itsf.sales.model.good.Good;
import fr.itsf.sales.model.good.Other;
import fr.itsf.sales.service.mapper.ReceiptMapper;
import fr.itsf.sales.service.mapper.ShoppingBasketMapper;
import fr.itsf.sales.service.mapper.UtilMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ShoppingBasketMapper shoppingBasketMapper;

    @Mock
    private ReceiptMapper receiptMapper;

    @Mock
    private TaxService taxService;

    @Mock
    private GoodFactory goodFactory;

    @Mock
    private UtilMapper utilMapper;

    @InjectMocks
    private ReceiptService receiptService;

    @Test
    void testGenerateReceipt_singleBook() {
        GoodSpec goodSpec = new GoodSpec();
        goodSpec.setName("book");
        goodSpec.setPrice(12.49);
        goodSpec.setQuantity(1);
        goodSpec.setCategory(GoodSpec.CategoryEnum.BOOK);
        goodSpec.setImported(false);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(1L);
        basketSpec.setGoods(List.of(goodSpec));

        Good book = new Book("book", new BigDecimal("12.49"), 1, false);
        ShoppingBasket basket = new ShoppingBasket(1L, List.of(book));

        TotalTaxAndPrice taxAndPrice = new TotalTaxAndPrice(BigDecimal.ZERO, new BigDecimal("12.49"));

        ReceiptSpec expectedReceiptSpec = new ReceiptSpec();

        when(shoppingBasketMapper.asShoppingBasket(any(), any(), any())).thenReturn(basket);
        when(taxService.calculateIncludedTaxPriceGood(any())).thenReturn(taxAndPrice);
        when(receiptMapper.asReceiptSpec(any())).thenReturn(expectedReceiptSpec);

        ReceiptSpec result = receiptService.generateReceipt(basketSpec);

        assertNotNull(result);
        verify(shoppingBasketMapper).asShoppingBasket(basketSpec, goodFactory, utilMapper);
        verify(taxService).calculateIncludedTaxPriceGood(book);
        verify(receiptMapper).asReceiptSpec(any(Receipt.class));
    }

    @Test
    void testGenerateReceipt_multipleGoods() {
        GoodSpec bookSpec = new GoodSpec();
        bookSpec.setName("book");
        bookSpec.setPrice(12.49);
        bookSpec.setQuantity(1);
        bookSpec.setCategory(GoodSpec.CategoryEnum.BOOK);
        bookSpec.setImported(false);

        GoodSpec cdSpec = new GoodSpec();
        cdSpec.setName("music CD");
        cdSpec.setPrice(14.99);
        cdSpec.setQuantity(1);
        cdSpec.setCategory(GoodSpec.CategoryEnum.OTHER);
        cdSpec.setImported(false);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(1L);
        basketSpec.setGoods(Arrays.asList(bookSpec, cdSpec));

        Good book = new Book("book", new BigDecimal("12.49"), 1, false);
        Good cd = new Other("music CD", new BigDecimal("14.99"), 1, false);
        ShoppingBasket basket = new ShoppingBasket(1L, Arrays.asList(book, cd));

        TotalTaxAndPrice bookTaxAndPrice = new TotalTaxAndPrice(BigDecimal.ZERO, new BigDecimal("12.49"));
        TotalTaxAndPrice cdTaxAndPrice = new TotalTaxAndPrice(new BigDecimal("1.50"), new BigDecimal("14.99"));

        ReceiptSpec expectedReceiptSpec = new ReceiptSpec();

        when(shoppingBasketMapper.asShoppingBasket(any(), any(), any())).thenReturn(basket);
        when(taxService.calculateIncludedTaxPriceGood(book)).thenReturn(bookTaxAndPrice);
        when(taxService.calculateIncludedTaxPriceGood(cd)).thenReturn(cdTaxAndPrice);
        when(receiptMapper.asReceiptSpec(any())).thenReturn(expectedReceiptSpec);

        ReceiptSpec result = receiptService.generateReceipt(basketSpec);

        assertNotNull(result);
        verify(taxService, times(2)).calculateIncludedTaxPriceGood(any());
        verify(receiptMapper).asReceiptSpec(any(Receipt.class));
    }

    @Test
    void testGenerateReceipt_importedGoods() {
        GoodSpec foodSpec = new GoodSpec();
        foodSpec.setName("imported box of chocolates");
        foodSpec.setPrice(10.00);
        foodSpec.setQuantity(1);
        foodSpec.setCategory(GoodSpec.CategoryEnum.FOOD);
        foodSpec.setImported(true);

        GoodSpec perfumeSpec = new GoodSpec();
        perfumeSpec.setName("imported bottle of perfume");
        perfumeSpec.setPrice(47.50);
        perfumeSpec.setQuantity(1);
        perfumeSpec.setCategory(GoodSpec.CategoryEnum.OTHER);
        perfumeSpec.setImported(true);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(2L);
        basketSpec.setGoods(Arrays.asList(foodSpec, perfumeSpec));

        Good food = new Food("imported box of chocolates", new BigDecimal("10.00"), 1, true);
        Good perfume = new Other("imported bottle of perfume", new BigDecimal("47.50"), 1, true);
        ShoppingBasket basket = new ShoppingBasket(2L, Arrays.asList(food, perfume));

        TotalTaxAndPrice foodTaxAndPrice = new TotalTaxAndPrice(new BigDecimal("0.50"), new BigDecimal("10.00"));
        TotalTaxAndPrice perfumeTaxAndPrice = new TotalTaxAndPrice(new BigDecimal("7.15"), new BigDecimal("47.50"));

        ReceiptSpec expectedReceiptSpec = new ReceiptSpec();

        when(shoppingBasketMapper.asShoppingBasket(any(), any(), any())).thenReturn(basket);
        when(taxService.calculateIncludedTaxPriceGood(food)).thenReturn(foodTaxAndPrice);
        when(taxService.calculateIncludedTaxPriceGood(perfume)).thenReturn(perfumeTaxAndPrice);
        when(receiptMapper.asReceiptSpec(any())).thenReturn(expectedReceiptSpec);

        ReceiptSpec result = receiptService.generateReceipt(basketSpec);

        assertNotNull(result);
        verify(taxService, times(2)).calculateIncludedTaxPriceGood(any());
    }

    @Test
    void testGenerateReceipt_emptyBasket() {
        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(3L);
        basketSpec.setGoods(List.of());

        ShoppingBasket basket = new ShoppingBasket(3L, List.of());
        ReceiptSpec expectedReceiptSpec = new ReceiptSpec();

        when(shoppingBasketMapper.asShoppingBasket(any(), any(), any())).thenReturn(basket);
        when(receiptMapper.asReceiptSpec(any())).thenReturn(expectedReceiptSpec);

        ReceiptSpec result = receiptService.generateReceipt(basketSpec);

        assertNotNull(result);
        verify(taxService, never()).calculateIncludedTaxPriceGood(any());
        verify(receiptMapper).asReceiptSpec(any(Receipt.class));
    }
}