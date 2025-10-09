package fr.itsf.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.itsf.model.GoodSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for exception handling in ReceiptController
 */
@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReceiptService receiptService;

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