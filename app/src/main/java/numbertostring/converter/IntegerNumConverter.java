package numbertostring.converter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import numbertostring.language.LanguageRules;
import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.pojo.IntegerNum;
import numbertostring.pojo.Number;


/**
 * This class is a subclass of a LocalizedNumberConverter.
 * Each {@code IntegerNumConverter} is tied to a specified locale and 
 * contains possibly empty fields to hold constants relating to the language's names
 * for numbers. Other fields include optional scientific notation formatting and a chunkSize that 
 * may vary depending on locale.
 * 
 * This class provides a method to convert integer values to the word form 
 * representation of the provided language. The current implementation assumes the numerical structure of 
 * English spoken numbers (ones, teens, tens) which may not hold for languages with different digit groupings 
 * (e.g. Japanese grouping by 10,000) or a different numerical structure 
 * (e.g. Hindi with lakh (100,000) and crore (10,000,000)). This implementation may be generalized to support
 * arbitrary digit groupings in the future.
 * 
 */
@Getter
public class IntegerNumConverter extends LocalizedNumberConverter<IntegerNum>{

    /** Default constructor assumes English for conversion */
    public IntegerNumConverter() {
        super(new DefaultLanguageRulesProvider().getLanguageRules(Locale.ENGLISH));
    }

    /**
     * Constructor with user-defined rules.
     * @param rules object holding language-specific constants
     */
    public IntegerNumConverter(LanguageRules rules) {
        super(rules); 
    }

    @Override
    public <T extends Number<T>> String convertToWords(T number) {
        if (!(number instanceof IntegerNum)) {
            throw new IllegalArgumentException("Unsupported number type.");
        }

        IntegerNum integerNum = (IntegerNum) number;
        return convertNumberToWords(integerNum);
    }

    /**
     * Converts an integer to its word representation.
     * @param num IntegerNum 
     * @return
     */
    private String convertNumberToWords(IntegerNum num) {
        if (num.equals(IntegerNum.ZERO)) {
            return rules.getWord(0);
        }

        // For numbers whose numeral representation is pre-computed, return its representation immediately.
        String smallNum;
        if (!(smallNum = rules.getWord(num.getValue().intValue())).isEmpty()) {
            return smallNum;
        } 

        boolean isNegative = num.isNegative();
        BigInteger absoluteInteger = num.getValue();
        if (isNegative) {
            absoluteInteger = absoluteInteger.negate();
        }
        StringBuilder output = new StringBuilder();
        String positiveString = processNumberRecursively(absoluteInteger);

        if (isNegative) {
            output.insert(0, " " + rules.getNegativeWord() + " " + positiveString);
            
        }
        return output.toString().trim();
    }

    /**
     * Processes numbers by breaking them into groupings.
     * Different languages can have different digit groupings for numbers that are sufficiently large.
     * E.g. Japanese using 4-digit groupings (10,000) vs English (1,000)
     * Future versions of this library can extend this function and its helper to use arbitrary chunkSizes.
     * @param num number to convert
     * @return
     */
    private String processNumberRecursively(BigInteger current) {
        StringBuilder result = new StringBuilder();
        
        while (current.compareTo(BigInteger.ZERO) > 0) {
            int smallestRecognized = getSmallestRecognizedPart(current);
            result.append(rules.getWord(smallestRecognized)).append(" ");
            current = current.subtract(BigInteger.valueOf(smallestRecognized));
        }
        return result.toString().trim();
    }

    /**
     * Helper method to get the smallest recognized integer that has a number that exists for it.
     * @param number
     * @return
     */
    private int getSmallestRecognizedPart(BigInteger number) {
        List<Integer> sortedKeys = new ArrayList<>(rules.getNumerals().keySet());
        Collections.sort(sortedKeys, Collections.reverseOrder());
        for (Integer key : sortedKeys) {
            if (BigInteger.valueOf(key).compareTo(number) <= 0) {
                return key;
            }
        }
        return -1; // Fallback in case the map does not cover the number
    }


}
