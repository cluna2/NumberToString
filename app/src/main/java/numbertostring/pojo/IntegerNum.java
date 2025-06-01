package numbertostring.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import numbertostring.converter.IntegerNumConverter;
import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.language.LanguageRules;




/**
 * Wrapper for the {@code BigInteger} class that subclasses a {@code Number}.
 * Uses {@code BigInteger} to store underlying value.
 * <p>
 * Has optional min/max range restrictions to represent 
 * arbitrarily large integers using {@code BigInteger}
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class IntegerNum extends Number<IntegerNum>{

    /** Zero constant for this class. */
    public static final IntegerNum ZERO = new IntegerNum(BigInteger.ZERO);

    /** The integer value stored using BigInteger.
     * @param value to store
     * @return value as BigInteger
     */
    private final BigInteger value;

    /**
     * Creates an IntegerNum instance using the value passed in.
     * @param value A {@code BigDecimal} representing underlyng value.
     */
    public IntegerNum(BigInteger value) {
        super(new BigDecimal(value));  // Enforces type consistency within Number<T>
        this.value = value;
    }

    /**
     * Creates an IntegerNumConverter instance using specific language rules.
     * @param rules Set of specific language rules for number conversion
     * @return Instance of IntegerNumConverter
     */
    @Override
    public LocalizedNumberConverter<IntegerNum> getConverter(LanguageRules rules) {
        return new IntegerNumConverter(rules);
    }
    /**
     * Determines sign of IntegerNum.
     * @return true if underlying value &lt; 0, and false otherwise.
     */
    public Boolean isNegative() {
        return this.value.signum() == -1;
    }

}
