package numbertostring.rules;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import numbertostring.language.NumberGrouping.ScaleType;

import com.google.common.math.BigIntegerMath;
/**
 * Defines a common structure for all positional numeral systems. Works as an
 * adapter between specific language rules and a LocalizedNumberConverter instance.
 * Holds a field to apply a function {@code f: (int, int) -> string} that applies language-specific 
 * rules.
 * 
 */
@ToString
@AllArgsConstructor

public class LanguageRules {

    /** Holds the scale type of the language. Useful for distinguishing alternative
     * powers of ten naming conventions. Most common types are the short and long scales.
     * @see <a href="https://en.wikipedia.org/wiki/Long_and_short_scales">Long and short scales</a>
     */
    @Getter
    private final ScaleType scaleType;

    /** String representing word for negative. If the number system does not allow negative numbers, leave as null. 
     * @return String word for negative.
    */
    @Getter
    private final String negativeWord;

    /** TreeMap of ints to numerals specific to each langauge.
     * These numerals are typically smaller than {@code grouping}.
     * The keys are guranteed to be sorted.
     * (e.g. in English, 90 is "ninety")
     * @return Numerals map
     */
    @Getter
    private final TreeMap<Integer, String> numeralsMap;

    /** Map of large integers to associated large unit name.
     * @return Map of largeUnits.
     */
    @Getter
    private final Map<BigInteger, String> largeUnits;

    /**
     * Function for custom numeral logic to return word representation of a number.
     * It takes in a list of a number smaller than {@code grouping} and a unit that will
     * be affixed to the number.
     * The unit provides additional context for languages.
     * (E.g. 100 = "One Hundred" in English versus "Cien" in Spanish)
     */
    @Getter
    private final Function<Integer, String> smallNumeralLogic;


    /** Function for custom unit logic to return word representation of a unit.
     * It takes in a power of the languages's number base (typically 10).
     * This function will return the correct word representation for the unit
     * depending on the use of short or long scale.
     */
    @Getter
    private final BiFunction<String, BigInteger, String> largeUnitLogic;

    /**
     * Applies the language's rules for numbers smaller than the 
     * number's current grouping integer. The grouping integer 
     * is determined by its scale.
     * 
     * @param num Number smaller than {@code groupingInteger}
     * @return String form of that number.
     */
    public String applyLanguageRulesForSmallNumbers(int num) {
        if (smallNumeralLogic != null) {
            return smallNumeralLogic.apply(num);
        }
        return "";
    }


    public String applyLanguageRulesForLargeUnits(String currentChunkString, BigInteger unit) {
        if (largeUnitLogic != null) {
            return largeUnitLogic.apply(currentChunkString, unit);
        }
        return "";
    }

    /** Given a number, return the appropriate grouping integer based
     * on the language's scale type.
     */
    public BigInteger getGrouping(BigInteger num) {
        int exponent = BigIntegerMath.log10(num, RoundingMode.DOWN);
        switch (scaleType) {
            case SHORT_SCALE:
                return BigInteger.valueOf(1_000);
            case LONG_SCALE:
                return exponent >= 9 ? BigInteger.valueOf(1_000_000): BigInteger.valueOf(1_000);
            default: 
                return BigInteger.valueOf(1_000);
        }
    }
}