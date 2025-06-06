package numbertostring.core.conversion;

import java.math.BigInteger;
import java.util.Locale;

import numbertostring.core.language.LocalizedNumberRulesRegistry;
import numbertostring.core.language.rules.LocalizedNumberRules;
import numbertostring.core.model.IntegerNum;
import numbertostring.core.model.Number;
import numbertostring.core.utils.logger.GlobalLogger;


/**
 * This class is a subclass of a LocalizedNumberConverter.
 * Each {@code IntegerNumConverter} is tied to a specified locale.
 * 
 * 
 * This class provides a method to convert integer values to the word form 
 * representation of the provided language. 
 * 
 */
public class IntegerNumConverter extends LocalizedNumberConverter{


    /** Default constructor assumes English for conversion */
    public IntegerNumConverter() {
        super(LocalizedNumberRulesRegistry.getRules(Locale.ENGLISH));
    }

    /**
     * Creates an instance aware of number conversion rules for user's desired language.
     * @param rules object holding language-specific constants
     */
    public IntegerNumConverter(LocalizedNumberRules rules) {
        super(rules); 
    }

    /**
     * Method to convert integers of any size to word form representation
     * of this instance's language.
     * @param number Number to convert.
     * @return Word form string.
     */
    @Override
    public String convertToWords(Number<?> number) {
        if (!(number instanceof IntegerNum)) {
            throw new IllegalArgumentException("Expected an IntegerNum instance, but received: " + number.getClass().getSimpleName());
        }
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
            return rules.applyNumeralRulesForZero();
        }

        boolean isNegative = num.isNegative();
        BigInteger absoluteInteger = num.getValue();
        if (isNegative) {
            absoluteInteger = absoluteInteger.negate();
        }
        StringBuilder output = new StringBuilder();
        String positiveString = processNumber(absoluteInteger);

        output.insert(0, positiveString);
        if (isNegative) {
            output.insert(0, rules.applyNegativeHandling(positiveString) + " ");   
        }
        return output.toString().trim();
    }

    /**
     * Processes numbers recursively by breaking them into chunks.
     * Delegates all chunk-processing logic to `LocalizedNumberRules`, ensuring flexibility for non-positional numeral systems.
     * @param num BigInteger number to convert.
     * @return Converted number.
     */
    
    private String processNumber(BigInteger num) {
        return rules.processNumberChunks(num);
    }
}
