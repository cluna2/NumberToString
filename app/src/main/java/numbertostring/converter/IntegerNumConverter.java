package numbertostring.converter;

import java.util.Locale;

import numbertostring.constants.LanguageConstants;
import numbertostring.pojo.IntegerNum;
import numbertostring.pojo.Number;

public class IntegerNumConverter extends LocalizedNumberConverter{
    private String[] ones = LanguageConstants.LANGUAGE_ONES.get(getLocale().getLanguage());
    private String[] teens = LanguageConstants.LANGUAGE_TEENS.get(getLocale().getLanguage());
    private String[] tens = LanguageConstants.LANGUAGE_TENS.get(getLocale().getLanguage());
    private String[] groupStrings = LanguageConstants.LANGUAGE_LARGE_GROUP_STRINGS.get(getLocale().getLanguage());
    private String hundred = LanguageConstants.LANGUAGE_HUNDRED.get(getLocale().getLanguage());
    
    
    private Integer chunkSize = 1000;
    public IntegerNumConverter() {
        super(Locale.ENGLISH);  // Explicitly pass default locale
    }

    /**
     * Constructor with user-defined Locale.
     */
    public IntegerNumConverter(Locale locale) {
        super(locale);  // Explicitly pass user-defined locale
    }

    @Override
    public String convertToWords(Number number) {
        if (!(number instanceof IntegerNum)) {
            throw new IllegalArgumentException("Unsupported number type.");
        }

        IntegerNum integerNum = (IntegerNum) number;
        int value = integerNum.getValue().intValue();

        return convertNumberToWords(value);
    }

    /**
     * Converts an integer to its word representation.
     * Handles Integer.MIN_VALUE as a special case to avoid underflow after negation.
     * @param num
     * @return
     */
    private String convertNumberToWords(Integer num) {
        String languageCode = getLocale().getLanguage();
        if (num.equals(0)) {
            return getZeroWord(languageCode);
        }

        if (num.equals(IntegerNum.MIN_VAL)) {
            return getMinValueWord(languageCode);
        }

        boolean isNegative = num < 0;
        if (isNegative) {
            num = -num;
        }
        StringBuilder output = new StringBuilder();
        String positiveString = processNumberRecursively(num);

        if (isNegative) {
            output.insert(0, " " + getNegativeWord(languageCode) + " " + positiveString);
            
        }
        return output.toString().trim();
    }

    /**
     * Processes numbers by breaking them into groupings of size chunkSize.
     * The chunkSize is set to English default of 1000. 
     * Different languages can have different digit groupings for numbers that are sufficiently large.
     * E.g. Japanese using 4-digit groupings (10,000) vs English (1,000)
     * Future versions of this library can extend this function and its helper to use arbitrary chunkSizes.
     * @param num number to convert
     * @return
     */
    private String processNumberRecursively(Integer number) {
        StringBuilder result = new StringBuilder();

        int unitGroup = 0;
        while (number > 0) {
            if (number % this.chunkSize != 0) {
                result.insert(0, processChunk(number % this.chunkSize) + " " + groupStrings[unitGroup] + " ");
            }
            number /= chunkSize;
            unitGroup++;
        }

        return result.toString().trim();
    }

    /**
     * Processes chunks of numbers that are sufficiently small. 
     * Currently supports numbers from -999 to 999 as default chunnkSize is 1000.
     * Can be extended in to support an arbitrary chunkSize.
     * @param number from -999 to 999  
     * @return string form of number chunk
     */
    private String processChunk(Integer smallNum) {
        StringBuilder result = new StringBuilder();

        if (smallNum >= 100) {
            result.append(ones[smallNum / 100]).append(" " + hundred + " ");
            smallNum %= 100;
        }

        if (smallNum >= 10 && smallNum < 20) {
            result.append(teens[smallNum - 10]).append(" ");
        } else {
            if (smallNum >= 20) {
                result.append(tens[smallNum / 10]).append(" ");
            }
            smallNum %= 10;
            if (smallNum > 0) {
                result.append(ones[smallNum]).append(" ");
            }
        }

        return result.toString().trim();
    }

    /**
     * 
     */
    @Override
    protected String getMinValueWord(String languageCode) {
        return LanguageConstants.LANGUAGE_INTEGER_MIN_VALUE.get(languageCode);
    }

}
