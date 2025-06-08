package numbertostring.core.language;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.google.common.math.BigIntegerMath;

import numbertostring.core.model.NumberBase;

/** Enum that defines how different languages may group numbers differently.
 * For example, the short and long scales are two alternative ways to group powers
 * of ten, even though the scales refer to the same number representation (base 10).
 */
public enum GroupingStrategy {
    SHORT_SCALE {
        @Override
        public BigInteger getGroupingValue(BigInteger num, NumberBase base) {
            return BigInteger.valueOf(base.getRadix()).pow(3);
        }
    },
    LONG_SCALE {
        @Override
        public BigInteger getGroupingValue(BigInteger num, NumberBase numberBase) {
            int exponent = getExponent(num);
            BigInteger base = BigInteger.valueOf(numberBase.getRadix());
            return exponent >= 9 ? base.pow(6) 
                : base.pow(3);
        }
    };

    public abstract BigInteger getGroupingValue(BigInteger num, NumberBase base);

    protected int getExponent(BigInteger num) {
        return BigIntegerMath.log10(num, RoundingMode.DOWN);
    }
}
