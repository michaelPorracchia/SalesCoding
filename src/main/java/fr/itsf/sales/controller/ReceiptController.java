package fr.itsf.sales.controller;

import fr.itsf.api.SalesSpec;
import fr.itsf.model.ReceiptSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptController implements SalesSpec {

    private final ReceiptService receiptService;

    @Override
    public ResponseEntity<ReceiptSpec> generateReceipt(ShoppingBasketSpec shoppingBasketSpec) {
        ReceiptSpec receipt = receiptService.generateReceipt(shoppingBasketSpec);
        return ResponseEntity.accepted().body(receipt);
    }
}
