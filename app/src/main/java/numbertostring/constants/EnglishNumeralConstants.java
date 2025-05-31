package numbertostring.constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Constants mapping number names to English words. Leading entries are null strings
 * for ease of implementation. Supports number-to-word conversion in {@code IntegerNumConverter}
*/
public class EnglishNumeralConstants {

    /**
     * Defaut constructor
     */
    public EnglishNumeralConstants() {}


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

    /** Numerals in English are grouped by thousands. */
    public static final boolean USES_THOUSANDS_GROUPING = true;
    /** Constant for minus word in English. */
    public static final String NEGATIVE_WORD = "Negative";

    /** Word form for special case of Integer.MIN_VALUE */
    public static final String MIN_INTEGER = "Negative Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Eight";

    /** English words for large unit names (thousand, million, billion). 
     * Larger units are left out for now as they exceed range of representability with 32-bit integers.
     * @see numbertostring.pojo.IntegerNum
    */
    public static final String[] LARGE_UNITS = {"", "Thousand", "Million", "Billion"}; 
}
