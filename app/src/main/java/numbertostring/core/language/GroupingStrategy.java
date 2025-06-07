package numbertostring.core.language;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.google.common.math.BigIntegerMath;

/** Enum that defines how different languages may group numbers differently.
 * For example, the short and long scales are two alternative ways to group powers
 * of ten, even though the scales refer to the same number representation (base 10).
 */
public enum GroupingStrategy {
    SHORT_SCALE {
        @Override
        public BigInteger getGroupingValue(BigInteger num) {
            return BigInteger.valueOf(1_000);
        }
    },
    LONG_SCALE {
        @Override
        public BigInteger getGroupingValue(BigInteger num) {
            return BigIntegerMath.log10(num, RoundingMode.DOWN) >= 9 
                ? BigInteger.valueOf(1_000_000) 
                : BigInteger.valueOf(1_000);
        }
    };

    public abstract BigInteger getGroupingValue(BigInteger num);
}
