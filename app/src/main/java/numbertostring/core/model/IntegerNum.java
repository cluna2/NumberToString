package numbertostring.core.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Builder;
import lombok.Getter;
import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LanguageRules;




/**
 * Wrapper for the {@code BigInteger} class that subclasses a {@code Number}.
 * Uses {@code BigInteger} to store underlying value.
 * <p>
 * Has optional min/max range restrictions to represent 
 * arbitrarily large integers using {@code BigInteger}
 * 
 */
@Builder
public class IntegerNum extends Number<IntegerNum>{


    /** The integer value stored using BigInteger.
     * @return value as BigInteger
     */
    @Getter
    private final BigInteger value;

    /**
     * Creates an IntegerNum instance using the value passed in.
     * @param value A {@code BigInteger} representing underlyng value.
     */
    public IntegerNum(BigInteger value) {
        super(new BigDecimal(value), IntegerNum.class);  // Enforces type consistency within Number<T>
        this.value = value;
    }

    /** Creates an IntegerNum instance using a BigDecimal as argument.
     * Note that precision loss may occur due to how Java makes the conversion
     * from BigDecimal to BigInteger.
     * @param value A {@code BigDecimal} representing underlying value.
     */
    public IntegerNum(BigDecimal value) {
        super(value, IntegerNum.class);
        this.value = value.toBigInteger();
    }
    
    /**
     * Creates an IntegerNumConverter instance using specific language rules.
     * @param rules Set of specific language rules for number conversion
     * @return Instance of IntegerNumConverter
     */
    @Override
    public LocalizedNumberConverter getConverter(LanguageRules rules) {
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
