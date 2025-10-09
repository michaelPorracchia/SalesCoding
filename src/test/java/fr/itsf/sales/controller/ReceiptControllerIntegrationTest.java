package fr.itsf.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.itsf.model.GoodSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReceiptService receiptService;

    @Test
    void testGenerateReceipt_input1() throws Exception {
        // Input 1: 2 books, 1 music CD, 1 chocolate bar
        GoodSpec book = createGoodSpec("book", 12.49, 2, GoodSpec.CategoryEnum.BOOK, false);
        GoodSpec cd = createGoodSpec("music CD", 14.99, 1, GoodSpec.CategoryEnum.OTHER, false);
        GoodSpec chocolate = createGoodSpec("chocolate bar", 0.85, 1, GoodSpec.CategoryEnum.FOOD, false);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(1L);
        basketSpec.setGoods(Arrays.asList(book, cd, chocolate));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$['Sales Taxes']").exists())
                .andExpect(jsonPath("$.Total").exists())
                .andExpect(jsonPath("$.ShoppingBasket").exists())
                .andExpect(jsonPath("$.ShoppingBasket.goods").isArray())
                .andExpect(jsonPath("$.ShoppingBasket.goods.length()").value(3));
    }

    @Test
    void testGenerateReceipt_input2() throws Exception {
        // Input 2: 1 imported box of chocolates, 1 imported bottle of perfume
        GoodSpec chocolates = createGoodSpec("imported box of chocolates", 10.00, 1, GoodSpec.CategoryEnum.FOOD, true);
        GoodSpec perfume = createGoodSpec("imported bottle of perfume", 47.50, 1, GoodSpec.CategoryEnum.OTHER, true);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(2L);
        basketSpec.setGoods(Arrays.asList(chocolates, perfume));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$['Sales Taxes']").exists())
                .andExpect(jsonPath("$.Total").exists());
    }

    @Test
    void testGenerateReceipt_input3() throws Exception {
        // Input 3: 1 imported bottle of perfume, 1 bottle of perfume, 1 packet of headache pills, 3 imported boxes of chocolates
        GoodSpec importedPerfume = createGoodSpec("imported bottle of perfume", 27.99, 1, GoodSpec.CategoryEnum.OTHER, true);
        GoodSpec perfume = createGoodSpec("bottle of perfume", 18.99, 1, GoodSpec.CategoryEnum.OTHER, false);
        GoodSpec pills = createGoodSpec("packet of headache pills", 9.75, 1, GoodSpec.CategoryEnum.MEDICAL, false);
        GoodSpec chocolates = createGoodSpec("imported box of chocolates", 11.25, 3, GoodSpec.CategoryEnum.FOOD, true);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(3L);
        basketSpec.setGoods(Arrays.asList(importedPerfume, perfume, pills, chocolates));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$['Sales Taxes']").exists())
                .andExpect(jsonPath("$.Total").exists())
                .andExpect(jsonPath("$.ShoppingBasket.goods.length()").value(4));
    }

    @Test
    void testGenerateReceipt_emptyBasket() throws Exception {
        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(4L);
        basketSpec.setGoods(List.of());

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGenerateReceipt_onlyExemptedGoods() throws Exception {
        GoodSpec book = createGoodSpec("book", 12.49, 1, GoodSpec.CategoryEnum.BOOK, false);
        GoodSpec chocolate = createGoodSpec("chocolate bar", 0.85, 1, GoodSpec.CategoryEnum.FOOD, false);
        GoodSpec pills = createGoodSpec("packet of headache pills", 9.75, 1, GoodSpec.CategoryEnum.MEDICAL, false);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(5L);
        basketSpec.setGoods(Arrays.asList(book, chocolate, pills));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$['Sales Taxes']").exists())
                .andExpect(jsonPath("$.Total").exists());
    }

    @Test
    void testGenerateReceipt_onlyTaxableGoods() throws Exception {
        GoodSpec cd = createGoodSpec("music CD", 14.99, 1, GoodSpec.CategoryEnum.OTHER, false);
        GoodSpec perfume = createGoodSpec("bottle of perfume", 18.99, 1, GoodSpec.CategoryEnum.OTHER, false);

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(6L);
        basketSpec.setGoods(Arrays.asList(cd, perfume));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGenerateReceipt_invalidCategory_shouldReturn412() throws Exception {
        String invalidCategory = """
                {
                    "id": 1,
                    "goods": [
                        {
                            "name": "test product",
                            "price": 10.00,
                            "quantity": 1,
                            "category": "INVALID_CATEGORY",
                            "imported": false
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCategory))
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("412"))
                .andExpect(jsonPath("$.message").value("Precondition Failed"));
    }

    @Test
    void testBadRequest_shouldReturn400() throws Exception {
        // Mock le service pour lever une IllegalArgumentException
        when(receiptService.generateReceipt(any())).thenThrow(new IllegalArgumentException("Invalid argument"));

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(1L);
        basketSpec.setGoods(List.of(createGoodSpec("test", 10.0, 1, GoodSpec.CategoryEnum.BOOK, false)));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Bad Request"));
    }

    @Test
    void testGenerateReceipt_malformedJson_shouldReturn412() throws Exception {
        String malformedJson = "{invalid json}";

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("412"))
                .andExpect(jsonPath("$.message").value("Precondition Failed"));
    }

    @Test
    void testGenerateReceipt_invalidPayload_shouldReturn412() throws Exception {
        String invalidPayload = "{}";

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("412"))
                .andExpect(jsonPath("$.message").value("Precondition Failed"));
    }

    @Test
    void testInternalServerError_shouldReturn500() throws Exception {
        // Mock le service pour lever une RuntimeException (erreur 500)
        when(receiptService.generateReceipt(any())).thenThrow(new RuntimeException("Unexpected error"));

        ShoppingBasketSpec basketSpec = new ShoppingBasketSpec();
        basketSpec.setId(1L);
        basketSpec.setGoods(List.of(createGoodSpec("test", 10.0, 1, GoodSpec.CategoryEnum.BOOK, false)));

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basketSpec)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    private GoodSpec createGoodSpec(String name, double price, int quantity, GoodSpec.CategoryEnum category, boolean imported) {
        GoodSpec spec = new GoodSpec();
        spec.setName(name);
        spec.setPrice(price);
        spec.setQuantity(quantity);
        spec.setCategory(category);
        spec.setImported(imported);
        return spec;
    }
}