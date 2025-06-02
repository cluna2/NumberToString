package numbertostring.converter;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import numbertostring.language.LanguageRules;
import numbertostring.logger.GlobalLogger;
import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.pojo.IntegerNum;
import numbertostring.pojo.Number;


/**
 * This class is a subclass of a LocalizedNumberConverter.
 * Each {@code IntegerNumConverter} is tied to a specified locale and 
 * 
 * 
 * This class provides a method to convert integer values to the word form 
 * representation of the provided language. 
 * 
 */
public class IntegerNumConverter extends LocalizedNumberConverter<IntegerNum>{

    /** Int gathered from set of language rules. Most languages group by 1000s. */
    private final int groupingInteger;

    /** Default constructor assumes English for conversion */
    public IntegerNumConverter() {
        super(new DefaultLanguageRulesProvider().getLanguageRules(Locale.ENGLISH));
        this.groupingInteger = 1000;
    }

    /**
     * Creates an instance aware of number conversion rules for user's desired language.
     * @param rules object holding language-specific constants
     */
    public IntegerNumConverter(LanguageRules rules) {
        super(rules); 
        this.groupingInteger = rules.getGrouping();
    }

    /**
     * Method to convert integers of any size to word form representation
     * of this instance's language.
     * @param number Number to convert.
     * @return Word form string.
     */
    @Override
    public String convertToWords(Number<IntegerNum> number) {
        if (!(number instanceof IntegerNum)) {
            throw new IllegalArgumentException("Unsupported number type.");
        }

        IntegerNum integerNum = (IntegerNum) number;
        GlobalLogger.LOGGER.info(
            String.format("Beginning conversion for %d.", integerNum.getValue())); 
        String result = convertNumberToWords(integerNum);
        GlobalLogger.LOGGER.debug(
            String.format("Conversion complete for %d. Output is \"%s\"",
                integerNum.getValue(), result)); 
        return result;
    }

    /**
     * Mmethod to handle zero and negative number cases before passing off
     * core conversion logic to helper methods.
     * @param num IntegerNum 
     * @return
     */
    private String convertNumberToWords(IntegerNum num) {
        if (num.equals(IntegerNum.ZERO)) {
            return rules.getWord(0);
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
            output.insert(0, " " + rules.getNegativeWord() + " ");   
        }
        return output.toString().trim();
    }

    /**
     * Processes positive numbers recursively by breaking them into chunks. Chunks are defined 
     * by the language's ways of grouping numbers.
     * (e.g. Japanese using 4-digit groupings (10,000) vs English (1,000))
     * @param num number to convert
     * @return Converted number.
     */
    private String processNumberByChunk(BigInteger current) {
        StringBuilder result = new StringBuilder();

        // Reduce the number % groupingNumber to process by chunks.
        while(current.compareTo(BigInteger.ZERO) > 0) {
            BigInteger chunk = current;
            BigInteger largestUnit = BigInteger.ONE;

            // Reduce chunk until it is smaller than the grouping number before processing.
            while (chunk.compareTo(BigInteger.valueOf(this.groupingInteger)) >= 0) {
                chunk = chunk.divide(BigInteger.valueOf(this.groupingInteger));
                largestUnit = largestUnit.multiply(BigInteger.valueOf(this.groupingInteger));
            }

            // Apply appropriate large unit if applicable after extracting numerals.
            if  (largestUnit.compareTo(BigInteger.ONE) > 0 && rules.getLargeUnits().containsKey(largestUnit)) {
                result.append(" " + processSmallNumbers(chunk.intValue()) + " ");
                result.append(rules.getLargeUnits().get(largestUnit));
            } else {
                result.append(" " + processSmallNumbers(current.intValue()));
            }
            current = current.mod(largestUnit);
        }
        return result.toString().trim();
    }

    
    /**
     * Helepr method to match small numbers to their word representation.
     * Uses custom function to return word string for special cases.
     * (e.g. French 70 {@literal ->} Soixante-dix ("Sixty-ten")).
     * 
     * If no special rules are needed, handles conversion of numbers 
     * that are smaller than the {@code groupingInteger}.
     * @param num Number smaller than {@code groupingInteger}
     * @return String form of that number.
     */
    private String processSmallNumbers(int num) {

        // Check if the language has special numeral rules
        // and if it does, if it applies to this number.
        Function<Integer, String> func;
        if ((func = rules.getCustomNumeralLogic()) != null) {
            if (func.apply(num) != null) {
                return func.apply(num);
            }
        }

        StringBuilder result = new StringBuilder();

        while (num > 0) {

            Map.Entry<Integer, String> entry = rules.getClosestNumeral(num);
            // Largest number that divides the input and has associated numeral.
            int biggestNum = entry.getKey();
            String numeral = entry.getValue();
            int factor = num / biggestNum;

            // Case: The number has a "One" that might need to be affixed
            if (factor == 1) {

                // Additional check if an explicit "One" needs to be affixed to matching numeral
                if (rules.requiresExplicitOne() && rules.isBaseUnit(biggestNum)) { 
                    result = new StringBuilder(result.append(
                        rules.getClosestNumeral(1).getValue() + " " + numeral)
                        .toString().trim());
                } else {
                    // If the language doesn't require a "One", just append the numeral
                    result = new StringBuilder(result.append(" " + numeral).toString().trim());
                }
            } 

            if (factor > 1) {
                result.append(rules.getWord(factor) + " ");
                result.append(numeral);
            }
            num = num % biggestNum;
        }

        return result.toString().trim();
    }

}
