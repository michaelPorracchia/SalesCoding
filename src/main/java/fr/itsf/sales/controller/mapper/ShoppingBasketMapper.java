package fr.itsf.sales.controller.mapper;

import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.model.ShoppingBasket;
import fr.itsf.sales.model.factory.GoodFactory;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {GoodMapper.class, UtilMapper.class})
public interface ShoppingBasketMapper {

    ShoppingBasketSpec asShoppingBasketSpec(ShoppingBasket shoppingBasket);

    ShoppingBasket asShoppingBasket(ShoppingBasketSpec shoppingBasketSpec,
                                    @Context GoodFactory factory,
                                    @Context UtilMapper utilMapper);
}
