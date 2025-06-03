package numbertostring.constants;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Constants mapping number names to English words. Leading entries are null strings
 * for ease of implementation. Supports number-to-word conversion for {@code LocalizedNumberConverter}
*/
public class EnglishNumeralConstants {

    /**
     * Private constructor. Do not instantiate this class.
     */
    private EnglishNumeralConstants() {}


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

    /** Determines how English numbers are grouped (i.e. by 1000s) */
    public static final Integer GROUPING = 1000;

    /** The "One" word must be prefixed to base units.  */
    public static final Boolean EXPLICIT_ONE = true;

    /** Set of units that may require a One prepended. In English, this is just the unit "Hundred". */
    public static final Set<Integer> BASE_UNITS = Set.of(100);

    /** Constant for minus word in English. */
    public static final String NEGATIVE_WORD = "Negative";


    /** Map of large units to associated English names.
     * Units are defined up to a Trillion
     * but can easily be extended by filling out the map with more names.
     * @see numbertostring.pojo.IntegerNum
    */
    public static final TreeMap<BigInteger, String> LARGE_UNITS = new TreeMap<BigInteger, String>(Map.ofEntries(
        Map.entry(new BigInteger("0"), ""), Map.entry(new BigInteger("1000"), "Thousand"),
        Map.entry(new BigInteger("1000000"), "Million"), Map.entry(new BigInteger("1000000000"), "Billion"),
        Map.entry(new BigInteger("1000000000000"), "Trillion")
    ));
}
