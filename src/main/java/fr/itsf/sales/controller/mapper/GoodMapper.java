package fr.itsf.sales.controller.mapper;

import fr.itsf.model.GoodSpec;
import fr.itsf.sales.model.factory.GoodFactory;
import fr.itsf.sales.model.good.Good;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = UtilMapper.class)
public interface GoodMapper {

    @Mappings({
            @Mapping(source = "price", target = "price", qualifiedByName = "bigDecimalToDouble"),
            @Mapping(target = "category", expression = "java(getCategoryFromGood(good))")
    })
    GoodSpec asGoodSpec(Good good);

    default GoodSpec.CategoryEnum getCategoryFromGood(Good good) {
        String className = good.getClass().getSimpleName().toLowerCase(java.util.Locale.ROOT);
        return GoodSpec.CategoryEnum.fromValue(className);
    }

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
