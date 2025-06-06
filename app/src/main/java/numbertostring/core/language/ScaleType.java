package numbertostring.core.language;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.google.common.math.BigIntegerMath;

public enum ScaleType {
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
