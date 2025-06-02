package numbertostring.language;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import numbertostring.logger.GlobalLogger;

/**
 * Defines a common structure for all language-specific positional numeral systems.
 * 
 * For numeral systems that do not follow predictable grouping patterns or do 
 * not have a strict base structure (e.g. Roman numerals, Tally marks, etc.), use the {@code customNumeralLogic} field
 * to implement rules.
 * 
 * 
 */
@ToString
@AllArgsConstructor

public class LanguageRules {

    /** Map of ints to numerals specific to each langauge.
     *  Typically these numerals are small (&le; 1000). 
     * (e.g. in English, 90 is "ninety")
     * @return Numerals map
     */
    @Getter
    private final TreeMap<Integer, String> numeralsMap;

    /** Map of large integers to associated large unit name.
     * @return Map of largeUnits.
     */
    @Getter
    private final TreeMap<BigInteger, String> largeUnits;

    /** Set of numbers that may require a "1" preprended. */
    @Getter
    private final Set<Integer> baseUnits; 
    
    /** Number that determines how numbers are grouped for using large units.
     * Most langauages will have this set to 1000. 
     * @return Grouping number.
    */
    @Getter
    private final Integer grouping;

    /** Flag set for certain languages that require the word for "One" to 
     * be prefixed to a base unit. 
     * True in English (that is, 100 -> "One Hundred" instead of "Hundred")
     * @return Explicit one flag.
     */
    @Getter    
    private final boolean needsExplicitOne;


    /** String representing word for negative. If the number system does not allow negative numbers, leave as null and 
     * use {@link customNumeralLogic} instead.
     * @return String word for negative.
    */
    @Getter
    private final String negativeWord;

    /**
     * Function for custom numeral logic to return word representation of a number.
     * Use this field if your language's numeral system has complex rules or has specific
     * excpetions (e.g. French numbers from 70-99).
     * The function should return null if the word cannot be found or if no special rule applies.
     * Otherwise, pass in {@code null}.
     */
    @Getter
    private final Function<Integer, String> customNumeralLogic;


    /** 
     * Retrieves language-specific word for a number if it exists in the numerals map.
     * For atypical number systems, custom rules are applied instead.
     * @param num Number to get word for.
     * @return Word representation as string if successful, and empty string otherwise.
     */
    public String getWord(int num) {
        if (customNumeralLogic != null) {
            GlobalLogger.LOGGER.debug("Custom rules function is not null. Applying");
            String customResult = customNumeralLogic.apply(num);
            if (customResult != null) {
                GlobalLogger.LOGGER.debug(
                    String.format("Non-null output of rules function.", customResult));
                return customResult;
            }
        }
        return numeralsMap.getOrDefault(num, "");
    }


    /**
     * Retrieves the closest lower or exact numeral representation if available.
     * @param num Number to check.
     * @return Closest recognized numeral.
     */
    public Map.Entry<Integer, String> getClosestNumeral(int num) {
        return numeralsMap.floorEntry(num);
    }


    /**  Checks if "One" should be prefixed for base units (like "Hundred")
     * @return Explicit "One" flag.
     */
    public boolean requiresExplicitOne() {
        return needsExplicitOne;
    }


    /** Checks if given number is a base unit in the given language.
     * @param num Num to check
     * @return Flag if num is a base unit.
     */
    public boolean isBaseUnit(int num) {
        return baseUnits.contains(num);
    }
}