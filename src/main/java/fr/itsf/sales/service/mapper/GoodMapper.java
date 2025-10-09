package fr.itsf.sales.service.mapper;

import fr.itsf.model.GoodSpec;
import fr.itsf.sales.model.factory.GoodFactory;
import fr.itsf.sales.model.good.Good;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for good business object creation based on the GoodSpec
 * <p>
 * @see GoodSpec
 */
@Mapper(componentModel = "spring", uses = UtilMapper.class)
public interface GoodMapper {

    /**
     * Convert a Good to a GoodSpec
     * @param good the good to convert into a DTO
     * @return
     */
    @Mapping(source = "price", target = "price", qualifiedByName = "bigDecimalToDouble")
    @Mapping(target = "category", expression = "java(getCategoryFromGood(good))")
    GoodSpec asGoodSpec(Good good);

    /**
     * Get the category from a Good
     * @param good the good required to get his category
     * @return
     */
    default GoodSpec.CategoryEnum getCategoryFromGood(Good good) {
        String className = good.getClass().getSimpleName().toLowerCase(java.util.Locale.ROOT);
        return GoodSpec.CategoryEnum.fromValue(className);
    }

    /**
     * Convert a GoodSpec to a Good
     * @param spec  the good to convert into the business object
     * @param factory the factory to build a good regarding its category
     * @param utilMapper mapper for utility methods
     * @return Good
     */
    default Good asGood(GoodSpec spec, @Context GoodFactory factory, @Context UtilMapper utilMapper) {
        return factory.createGood(
                spec.getCategory(),
                spec.getName(),
                utilMapper.doubleToBigDecimal(spec.getPrice()),
                spec.getQuantity() != null ? spec.getQuantity() : 0,
                Boolean.TRUE.equals(spec.getImported())
        );
    }
}
