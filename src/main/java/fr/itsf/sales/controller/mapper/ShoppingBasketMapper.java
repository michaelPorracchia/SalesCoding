package fr.itsf.sales.controller.mapper;

import fr.itsf.model.ShoppingBasketSpec;
import fr.itsf.sales.model.ShoppingBasket;
import fr.itsf.sales.model.factory.GoodFactory;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * Mapper for shopping basket business object creation based on the ShoppingBasketSpec
 *
 * @see ShoppingBasket
 */
@Mapper(componentModel = "spring", uses = {GoodMapper.class, UtilMapper.class})
public interface ShoppingBasketMapper {

    /**
     * Convert a ShoppingBasket to a ShoppingBasketSpec
     * @param shoppingBasket the shopping basket business object
     * @return ShoppingBasketSpec
     */
    ShoppingBasketSpec asShoppingBasketSpec(ShoppingBasket shoppingBasket);

    /**
     * Convert a ShoppingBasketSpec to a ShoppingBasket
     * @param shoppingBasketSpec the shopping basket specification
     * @param factory the factory for Good business objects
     * @param utilMapper mapper for utility methods
     * @return ShoppingBasket
     */
    ShoppingBasket asShoppingBasket(ShoppingBasketSpec shoppingBasketSpec,
                                    @Context GoodFactory factory,
                                    @Context UtilMapper utilMapper);
}
