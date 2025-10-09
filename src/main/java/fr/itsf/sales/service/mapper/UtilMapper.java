package fr.itsf.sales.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;

/**
 * Mapper for utility methods
 * <p>
 * In our case, convertions between BigDecimal and Double
 */
@Mapper(componentModel = "spring")
public interface UtilMapper {

    /**
     * Convert a BigDecimal to a Double
     * @param value the value to convert
     * @return Double
     */
    @Named("bigDecimalToDouble")
    default Double bigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    /**
     * Convert a Double to a BigDecimal
     * @param value the value to convert
     * @return BigDecimal
     */
    @Named("doubleToBigDecimal")
    default BigDecimal doubleToBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}
