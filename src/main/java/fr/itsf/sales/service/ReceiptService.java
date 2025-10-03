package fr.itsf.sales.service;

import fr.itsf.model.ReceiptSpec;
import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.model.Receipt;
import fr.itsf.sales.model.TotalTaxAndPrice;
import fr.itsf.sales.model.factory.GoodFactory;
import fr.itsf.sales.service.mapper.ReceiptMapper;
import fr.itsf.sales.service.mapper.ShoppingBasketMapper;
import fr.itsf.sales.service.mapper.UtilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for receipt generation
 * <p>
 * Processing the shopping basket by applying applyable tax for each row and generate the receipt containing
 * the total taxes, the total price and the Good list with all included taxes price
 */
@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ShoppingBasketMapper shoppingBasketMapper;
    private final ReceiptMapper receiptMapper;
    private final TaxService taxService;
    private final GoodFactory goodFactory;
    private final UtilMapper utilMapper;

    /**
     * Generate a receipt from a shopping basket
     * @param shoppingBasketSpec the shopping basket specification
     * @return ReceiptSpec
     */
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
