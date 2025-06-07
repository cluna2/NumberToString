package numbertostring.core.language.rules;

import java.math.BigInteger;

import numbertostring.core.exception.NumberProcessingException;
import numbertostring.core.language.GroupingStrategy;
import numbertostring.core.language.processing.PositionalNumberProcessor;
import numbertostring.core.model.NumberBase;

/**
 * Defines a common structure for numeral systems across languages.
 * Allows language-specific rules to be implemented dynamically.
 */
public abstract class LocalizedNumeralRules {

    
    /**   */
    public LocalizedNumeralRules() {}
    
    /** Getter for the Locale that this rules instance is implementing rules for.  */
    public abstract String getLanguageCode();

    /** Returns whether the numeral system is positional or non-positional */
    public abstract boolean isPositionalSystem();

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
    public abstract String applyNumeralRulesForLargeUnits(String chunk, BigInteger unit);

    /** Handles custom logic for non-positional numeral systems */
    public abstract String applyNonPositionalConversion(BigInteger num);


    /** Processes number chunks in positional number systems with the positional number processor
     * Non-positional number systems will rely on the conversion rules defined in their classes.
     */
    public String processNumber(BigInteger num) {
        if (isPositionalSystem()) {
            return new PositionalNumberProcessor(this).processChunks(num);
        }
        return applyNonPositionalConversion(num);
    }

    public String applySmallNumeralRules(BigInteger chunk) {
        if (chunk == null) {
            throw new NumberProcessingException("Provided chunk is null");
        }   
        return applyNumeralRulesForSmallNumbers(chunk.intValue());
    }

    public String applyLargeUnitsRules(String chunkString, BigInteger unit) {
        if (unit == null || chunkString.isEmpty()) {
            throw new NumberProcessingException("Chunk string or unit is null.");
        }
        return applyNumeralRulesForLargeUnits(chunkString, unit);
    }
}
