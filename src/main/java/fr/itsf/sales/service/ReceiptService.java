package fr.itsf.sales.service;

import fr.itsf.model.ReceiptSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.controller.mapper.ReceiptMapper;
import fr.itsf.sales.controller.mapper.ShoppingBasketMapper;
import fr.itsf.sales.controller.mapper.UtilMapper;
import fr.itsf.sales.model.Receipt;
import fr.itsf.sales.model.TotalTaxAndPrice;
import fr.itsf.sales.model.factory.GoodFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ShoppingBasketMapper shoppingBasketMapper;
    private final ReceiptMapper receiptMapper;
    private final TaxService taxService;
    private final GoodFactory goodFactory;
    private final UtilMapper utilMapper;

    public ReceiptSpec generateReceipt(ShoppingBasketSpec shoppingBasketSpec) {
        Receipt receipt = new Receipt(shoppingBasketMapper.asShoppingBasket(shoppingBasketSpec, goodFactory, utilMapper));
        TotalTaxAndPrice sumTotalTaxAndPrice = new TotalTaxAndPrice(BigDecimal.ZERO, BigDecimal.ZERO);

        receipt.getShoppingBasket().getGoods().forEach(good -> {
                    TotalTaxAndPrice totalTaxAndPrice = new TotalTaxAndPrice(taxService.calculateIncludedTaxPriceGood(good));
                    good.setPrice(totalTaxAndPrice.getIncludedTaxPriceGood());
                    sumTotalTaxAndPrice.setTaxAmount(sumTotalTaxAndPrice.getTaxAmount()
                            .add(totalTaxAndPrice.getTaxAmount()));
                    sumTotalTaxAndPrice.setIncludedTaxPriceGood(sumTotalTaxAndPrice.getIncludedTaxPriceGood()
                            .add(totalTaxAndPrice.getIncludedTaxPriceGood()));
                });
        receipt.setSalesTaxes(sumTotalTaxAndPrice.getTaxAmount());
        receipt.setTotal(sumTotalTaxAndPrice.getIncludedTaxPriceGood());

        return receiptMapper.asReceiptSpec(receipt);
    }
}
