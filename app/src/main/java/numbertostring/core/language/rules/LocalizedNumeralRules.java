package numbertostring.core.language.rules;

import java.math.BigInteger;

import numbertostring.core.exception.NumberProcessingException;
import numbertostring.core.language.GroupingStrategy;
import numbertostring.core.model.NumberBase;

/**
 * Defines a common structure for numeral systems across languages.
 * Allows language-specific rules to be implemented dynamically.
 */
public abstract class LocalizedNumeralRules {

    
    public LocalizedNumeralRules() {}
    
    /** Getter for the Locale that this rules instance is implementing rules for.  */
    public abstract String getLanguageCode();

    /** Gets the radix (i.e. base) of the number system used. */
    public abstract NumberBase getNumberBase();

    /** Returns the scale type used by the language (Short Scale, Long Scale, etc.) */
    public abstract GroupingStrategy getGroupingStrategy();

    /** Converts zero into its word representation */
    public abstract String applyNumeralRulesForZero();

    /** Formats negative numbers correctly for the numeral system. */
    public abstract String applyNegativeHandling(String numberString);

    /** Converts a small number (less than the languages's largest grouping integer) into its word representation */
    public abstract String applyNumeralRulesForSmallNumbers(int num);

    /** Converts a large unit into its word representation */
    public abstract String getLargeUnitName(BigInteger largeUnit);

    /** Handles custom logic for non-positional numeral systems */
    public abstract String applyNonPositionalConversion(BigInteger num);


    public boolean isPositionalSystem() {
        return getNumberBase().isPositional();
    }

    public String applySmallNumeralRules(BigInteger chunk) {
        if (chunk == null) {
            throw new NumberProcessingException("Provided chunk is null");
        }   
        return applyNumeralRulesForSmallNumbers(chunk.intValue());
    }

    public String applyLargeUnitsRules(BigInteger largeUnit) {
        if (largeUnit == null) {
            throw new NumberProcessingException("Chunk string or unit is null.");
        }
        return getLargeUnitName(largeUnit);
    }
}
