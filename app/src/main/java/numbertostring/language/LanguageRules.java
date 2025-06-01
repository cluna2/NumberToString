package numbertostring.language;

import java.util.TreeMap;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Defines a common structure for all language-specific positional numeral systems.
 * Implicitly assumes the language provided has words for numbers .
 * For example, in English no new
 * <p>
 * For numeral systems that do not follow predictable grouping patterns or do 
 * not have a strict base structure (e.g. Roman numerals, Tally marks, etc.), use the {@code customNumeralLogic} field
 * to implement rules 
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
    private final TreeMap<Integer, String> numerals;

    /** Array of large units specific to each language (e.g. English - ["thousands", "millions", "billions"]) 
     * @return String array of large units
     */
    @Getter
    private final String[] largeUnits;

    /** Boolean to determine whether numbers should be grouped by thousands. True in most numeral systems. 
     * @return true if language uses 3-digit groupings and false otherwise.
    */
    @Getter
    private final boolean usesThousandsGrouping;

    /** String representing word for negative. If the number system does not allow negative numbers, leave as null and 
     * use {@link customNumeralLogic} instead.
     * @return string word for negative.
    */
    @Getter
    private final String negativeWord;

    /**
     * Function for custom numeral logic to return word representation of a number.
     * Use this field if your language's numeral system is non-positional or does not allow negative numbers.
     * Otherwise, pass in {@code null}
     */
    private final Function<Integer, String> customNumeralLogic;


    /** 
     * Retrieves language-specific word for a number if it exists in the numerals map.
     * 
     * For non-positional number systems, custom rules are applied instead.
     * @param num number to get word for
     * @return word representation as string if successful, and empty string otherwise
     */
    public String getWord(int num) {
        if (customNumeralLogic != null) {
            return customNumeralLogic.apply(num);
        } 
        return numerals.getOrDefault(num, "");
    }
}