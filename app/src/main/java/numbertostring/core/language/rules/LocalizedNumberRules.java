package numbertostring.core.language.rules;

import java.math.BigInteger;
import java.util.Map;
import numbertostring.core.language.ScaleType;

/**
 * Defines a common structure for numeral systems across languages.
 * Allows language-specific rules to be implemented dynamically.
 */
public abstract class LocalizedNumberRules {

    /** Returns the scale type used by the language (Short Scale, Long Scale, etc.) */
    public abstract ScaleType getScaleType();

    /** Returns the word for negative numbers in the language */
    public abstract String getNegativeWord();

    /** Returns a map of small numbers (e.g., 1-100) to their word equivalents */
    public abstract Map<Integer, String> getNumeralsMap();

    /** Returns a map of large units (Thousand, Million, Billion) for the language */
    public abstract Map<BigInteger, String> getLargeUnitsMap();

    /** Converts a small number (less than the languages's largest grouping integer) into its word representation */
    public abstract String applyNumeralRulesForSmallNumbers(int num);

    /** Converts a large unit into its word representation */
    public abstract String applyNumeralRulesForLargeUnits(String chunk, BigInteger unit);

    /** Handles number chunk processing for non-positional numeral systems */
    public abstract String processNumberChunks(BigInteger num);

    /** Handles negative number formatting dynamically */
    public String applyNegativeHandling(String numberString) {
        return getNegativeWord();
    }

    /** Handles zero conversion dynamically */
    public String applyNumeralRulesForZero() {
        return getNumeralsMap().getOrDefault(0, "Zero");
    }
}
