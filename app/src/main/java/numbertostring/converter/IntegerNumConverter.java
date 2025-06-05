package numbertostring.converter;

import java.math.BigInteger;
import java.util.Locale;

import numbertostring.logger.GlobalLogger;
import numbertostring.pojo.IntegerNum;
import numbertostring.pojo.Number;
import numbertostring.rules.DefaultLanguageRulesProvider;
import numbertostring.rules.LanguageRules;


/**
 * This class is a subclass of a LocalizedNumberConverter.
 * Each {@code IntegerNumConverter} is tied to a specified locale.
 * 
 * 
 * This class provides a method to convert integer values to the word form 
 * representation of the provided language. 
 * 
 */
public class IntegerNumConverter extends LocalizedNumberConverter<IntegerNum>{


    /** Default constructor assumes English for conversion */
    public IntegerNumConverter() {
        super(new DefaultLanguageRulesProvider().getLanguageRules(Locale.ENGLISH));
    }

    /**
     * Creates an instance aware of number conversion rules for user's desired language.
     * @param rules object holding language-specific constants
     */
    public IntegerNumConverter(LanguageRules rules) {
        super(rules); 
    }

    /**
     * Method to convert integers of any size to word form representation
     * of this instance's language.
     * @param number Number to convert.
     * @return Word form string.
     */
    @Override
    public String convertToWords(Number<IntegerNum> number) {
        IntegerNum integerNum = (IntegerNum) number;
        GlobalLogger.LOGGER.debug(
            String.format("Beginning conversion for %d.", integerNum.getValue())); 
        String result = convertNumberToWords(integerNum);
        GlobalLogger.LOGGER.debug(
            String.format("Conversion complete for %d. Output is \"%s\"",
                integerNum.getValue(), result)); 
        return result;
    }

    /**
     * Method to handle zero and negative number cases before passing off
     * core conversion logic to helper methods.
     * @param num Number to convert.
     * @return Word form string.
     */
    private String convertNumberToWords(IntegerNum num) {
        if (num.getValue().equals(BigInteger.ZERO)){
            return rules.getNumeralsMap().get(0);
        }

        boolean isNegative = num.isNegative();
        BigInteger absoluteInteger = num.getValue();
        if (isNegative) {
            absoluteInteger = absoluteInteger.negate();
        }
        StringBuilder output = new StringBuilder();
        String positiveString = processNumberByChunk(absoluteInteger);

        output.insert(0, positiveString);
        if (isNegative) {
            output.insert(0, rules.getNegativeWord() + " ");   
        }
        return output.toString().trim();
    }

    /**
     * Processes positive numbers recursively by breaking them into chunks. Chunks are defined 
     * by the language's ways of grouping numbers.
     * (e.g. Japanese using 4-digit groupings (10,000) vs English (1,000))
     * @param num BigInteger number to convert.
     * @return Converted number.
     */
    private String processNumberByChunk(BigInteger current) {
        StringBuilder result = new StringBuilder();

        // Reduce the number mod(groupingNumber) to process by chunks.
        while(current.compareTo(BigInteger.ZERO) > 0) {

            // The grouping number may change dynamically depending on a lanugage's scale.
            BigInteger groupingInteger = rules.getGrouping(current);
            BigInteger chunk = current;
            BigInteger largestUnit = BigInteger.ONE;

            // Reduce a chunk until it is smaller than the grouping number before processing.
            while (chunk.compareTo(groupingInteger) >= 0) {
                chunk = chunk.divide(groupingInteger);
                largestUnit = largestUnit.multiply(groupingInteger);
            }

            String chunkString = rules.applyLanguageRulesForSmallNumbers(chunk.intValue());

            // Apply appropriate large unit if applicable after extracting numerals.
            if (largestUnit.compareTo(BigInteger.ONE) > 0 && rules.getLargeUnits().containsKey(largestUnit)) {
                String modifiedChunkStringWithUnits = rules.applyLanguageRulesForLargeUnits(chunkString, largestUnit); 
                result.append(modifiedChunkStringWithUnits).append(" ");
            } else {
                result.append(chunkString).append(" ");
            }
            current = current.mod(largestUnit);
        }
        return result.toString().trim();
    }

    
}
