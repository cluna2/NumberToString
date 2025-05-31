package numbertostring.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;



/**
 * Wrapper for the {@code Integer} class that subclasses a {@code Number}.
 * Uses {@code BigInteger} to store underlying value.
 * This implementaion defaults to a 32-bit integer range.
 * <p>
 * Has optional min/max range restrictions to represent 
 * arbitrarily large integers using {@code BigInteger}
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
public class IntegerNum extends Number<IntegerNum>{

    /** Zero constant for this class. */
    public static final IntegerNum ZERO = new IntegerNum(BigInteger.ZERO);

    /** The integer value stored using BigInteger */
    private final BigInteger value;
     /** 
     * Minimum integer value this instance can hold.
     */
    private final BigInteger minValue;

    /** 
     * Maximum integer value this class can hold.
     */
    private final BigInteger maxValue;

    /** 
     * Constructs an IntegerNum instance with a default 32-bit rnage.
     * @param value A {@code BigInteger} representing the underlying value of the {@code IntegerNum}.
     */
    public IntegerNum(BigInteger value) {
        this(value, BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(Integer.MAX_VALUE));
    }

    /**
     * Constructor using input value and specifying the allowed range for this IntegerNum.
     * @param value A {@code BigDecimal} representing underlyng value. Must fall in between the specified range.
     * @param minValue A {@code BigInteger} determining the smallest allowable value
     * @param maxValue A {@code BigInteger} determining the largest allowable value
     */
    public IntegerNum(BigInteger value, BigInteger minValue, BigInteger maxValue) {
        super(new BigDecimal(value));  // Enforces type consistency within Number<T>
        if (minValue != null && maxValue != null && (value.compareTo(minValue) < 0 || value.compareTo(maxValue) > 0)) {
            throw new IllegalArgumentException("Number out of range! Allowed range: " + minValue + " to " + maxValue);
        }
        this.value = value;
        this.minValue = minValue != null ? minValue : BigInteger.valueOf(Integer.MIN_VALUE);
        this.maxValue = maxValue != null ? maxValue : BigInteger.valueOf(Integer.MAX_VALUE);
    }

    /**
     * Determines sign of IntegerNum.
     * @return true if underlying value &lt; 0, and false otherwise.
     */
    public Boolean isNegative() {
        return this.value.signum() == -1;
    }

}
