package numbertostring.core.conversion;

import java.math.BigInteger;
import java.util.Locale;

import numbertostring.core.language.LocalizedNumberRulesRegistry;
import numbertostring.core.language.rules.LocalizedNumeralRules;
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
    public IntegerNumConverter(LocalizedNumeralRules rules) {
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

        if (integerNum.getValue().equals(BigInteger.ZERO)){
            return rules.applyNumeralRulesForZero();
        }

        boolean isNegative = integerNum.isNegative();
        BigInteger absoluteValue = isNegative ? integerNum.getValue().negate() : integerNum.getValue();

        String result = rules.processNumber(absoluteValue);

        if (isNegative) {
            result = rules.applyNegativeHandling(result);
        }
        GlobalLogger.LOGGER.debug(
            String.format("Conversion complete for %d. Output is \"%s\"",
                integerNum.getValue(), result)); 

        return result.trim();
    }
}
