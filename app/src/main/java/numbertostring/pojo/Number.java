package numbertostring.pojo;

import java.math.BigDecimal;

import org.checkerframework.checker.units.qual.C;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@RequiredArgsConstructor
/**
 * Generic class to represent a number across different numeric systems.

  * @param <T> The type parameter representing the underlying numeric value.
 */
public abstract class Number<T extends Number<T>> {

    /** Underlying value of number in base 10. Stored using value type T
     * @param value the value of this number
     * */
    protected final BigDecimal value;

    /**
     * Returns implementing type.
     * 
     * @return The class type of the implementing instance.
     */
    public Class<T> getType() {
        return (Class<T>) this.getClass();
    }

}
