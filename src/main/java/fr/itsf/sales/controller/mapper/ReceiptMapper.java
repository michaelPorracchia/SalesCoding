package fr.itsf.sales.controller.mapper;

import fr.itsf.model.ReceiptSpec;
import fr.itsf.sales.model.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ShoppingBasketMapper.class, GoodMapper.class, UtilMapper.class})
public interface ReceiptMapper {

    @Mappings({
            @Mapping(source = "salesTaxes", target = "salesTaxes", qualifiedByName = "bigDecimalToDouble"),
            @Mapping(source = "total", target = "total", qualifiedByName = "bigDecimalToDouble")
    })
    ReceiptSpec asReceiptSpec(Receipt receipt);
}
