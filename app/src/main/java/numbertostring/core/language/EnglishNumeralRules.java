package numbertostring.core.language;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Constants mapping number names to English words. Leading entries are null strings
 * for ease of implementation. Supports number-to-word conversion for {@code LocalizedNumberConverter}
*/
public class EnglishNumeralRules {

    public EnglishNumeralRules() {}

    /** Type of scale used in English. */
    public static final ScaleType SCALE_TYPE = ScaleType.SHORT_SCALE;

    /** Constant for minus word in English. */
    public static final String NEGATIVE_WORD = "Negative";



    /** Map of ints to English numeral words up to one hundred. Uses a TreeMap to guarantee ascending order
     * of the keys. Supports processing by chunks. */
    public static final TreeMap<Integer, String> NUMERALS = new TreeMap<Integer, String>(Map.ofEntries(
        Map.entry(0, "Zero"), Map.entry(1, "One"), Map.entry(2, "Two"), Map.entry(3, "Three"),
        Map.entry(4, "Four"), Map.entry(5, "Five"), Map.entry(6, "Six"), Map.entry(7, "Seven"),
        Map.entry(8, "Eight"), Map.entry(9, "Nine"), Map.entry(10, "Ten"),
        Map.entry(11, "Eleven"), Map.entry(12, "Twelve"), Map.entry(13, "Thirteen"), Map.entry(14, "Fourteen"),
        Map.entry(15, "Fifteen"), Map.entry(16, "Sixteen"), Map.entry(17, "Seventeen"), Map.entry(18, "Eighteen"),
        Map.entry(19, "Nineteen"), Map.entry(20, "Twenty"), Map.entry(30, "Thirty"), Map.entry(40, "Forty"),
        Map.entry(50, "Fifty"), Map.entry(60, "Sixty"), Map.entry(70, "Seventy"), Map.entry(80, "Eighty"),
        Map.entry(90, "Ninety"), Map.entry(100, "Hundred")
    ));


    /** Map of large units to associated English names.
     * Units are defined up to a Trillion
     * but can easily be extended by filling out the map with more names.
    */
    public static final Map<BigInteger, String> LARGE_UNITS = new TreeMap<BigInteger, String>(Map.ofEntries(
        Map.entry(BigInteger.valueOf(1000), "Thousand"),
        Map.entry(BigInteger.valueOf(1_000_000), "Million"),
        Map.entry(BigInteger.valueOf(1_000_000_000), "Billion"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000L), "Trillion"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000L), "Quadrillion"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000_000L), "Quintillion"),
        // Beyond quinitillions is out of range of int literals, so we use string form as argument
        Map.entry(new BigInteger("1000000000000000000000"), "Sextillion"),
        Map.entry(new BigInteger("1000000000000000000000000"), "Septillion"),
        Map.entry(new BigInteger("1000000000000000000000000000"), "Octillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000"), "Nonillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000"), "Decillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000000"), "Undecillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000000000"), "Duodecillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000000000000"), "Tredecillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000000000000000"), "Quattuordecillion"),
        Map.entry(new BigInteger("1000000000000000000000000000000000000000000000000"), "Quindecillion")
    ));

    /** Function to apply English-specific rules for numbers less than {@code GROUPING}. */

    public static final Function<Integer, String> SMALL_NUMERAL_LOGIC = (num)-> {
        StringBuilder result = new StringBuilder();
        if (num >= 100) {
            result.append(NUMERALS.get(num / 100)).append(" Hundred ");
            num %= 100;
        }

        if (num >= 20) {
            result.append(NUMERALS.get(num - (num % 10))).append(" ");
            num %= 10;
            if (num > 0) {
                result.append(NUMERALS.get(num));
            }
        } else if (num >= 10) {
            result.append(NUMERALS.get(num));
        } else if (num >= 1 && num < 10) {
            result.append(NUMERALS.get(num));
        }
        return result.toString().trim();
    };

    /** Function to handle special rules for English. */
    public static final BiFunction<String, BigInteger, String> LARGE_UNITS_LOGIC = (chunkString, largeUnit)-> {
        String modifiedChunkString = chunkString;
        if (LARGE_UNITS.containsKey(largeUnit)) {
            String unitName = LARGE_UNITS.get(largeUnit);
            modifiedChunkString = chunkString + " " + unitName + " ";
        } 
        return modifiedChunkString.trim();
    };

}
