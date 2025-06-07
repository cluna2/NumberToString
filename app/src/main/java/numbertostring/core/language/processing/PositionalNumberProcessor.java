package numbertostring.core.language.processing;

import java.math.BigInteger;
import numbertostring.core.language.rules.LocalizedNumeralRules;

/**
 * Processes positional numeral systems by grouping numbers correctly before conversion.
 * Supports dynamic numeral grouping (e.g., 1,000 for English, 10,000 for Japanese).
 */
public class PositionalNumberProcessor {

    private final LocalizedNumeralRules rules;

    public PositionalNumberProcessor(LocalizedNumeralRules rules) {
        this.rules = rules;
    }

    /**
     * Processes numbers recursively based on the numeral system's grouping.
     * Used by positional numeral systems (English, Spanish, etc.).
     * Calls on rules objects to handle chunk and large unit conversions.
     * @param num BigInteger number to process.
     * @return Converted number in word format.
     */
    public String processChunks(BigInteger num) {
        StringBuilder result = new StringBuilder();
        while (num.compareTo(BigInteger.ZERO) > 0) {
            BigInteger groupingInteger = rules.getGroupingStrategy().getGroupingValue(num);
            BigInteger chunk = num;
            BigInteger largestUnit = BigInteger.ONE;

            while (chunk.compareTo(groupingInteger) >= 0) {
                chunk = chunk.divide(groupingInteger);
                largestUnit = largestUnit.multiply(groupingInteger);
            }

            String chunkString = rules.applySmallNumeralRules(chunk);

            if (largestUnit.compareTo(BigInteger.ONE) > 0) {
                result.append(rules.applyLargeUnitsRules(chunkString, largestUnit)).append(" ");
            } else {
                result.append(chunkString).append(" ");
            }
            num = num.mod(largestUnit);
        }
        return result.toString().trim();
    }
}
