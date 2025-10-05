package fr.itsf.sales.controller.mapper;

import fr.itsf.model.ReceiptSpec;
import fr.itsf.sales.model.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for receipt business object creation based on the ReceiptSpec
 * <p>
 * @see ReceiptSpec
 */
@Mapper(componentModel = "spring", uses = {ShoppingBasketMapper.class, GoodMapper.class, UtilMapper.class})
public interface ReceiptMapper {

    /**
     * Convert a Receipt to a ReceiptSpec
     * @param receipt the receipt to convert to DTO receipt
     * @return ReceiptSpec
     */
    @Mapping(source = "salesTaxes", target = "salesTaxes", qualifiedByName = "bigDecimalToDouble")
    @Mapping(source = "total", target = "total", qualifiedByName = "bigDecimalToDouble")
    ReceiptSpec asReceiptSpec(Receipt receipt);
}
