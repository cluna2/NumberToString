package numbertostring.converter;


import java.util.Locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.constants.LanguageConstants;
import numbertostring.pojo.Number;

/*
 * Abstract class to convert numbers to word form using locale.
 * Subclasses are based on the type of number to convert.
 */
@Getter
@RequiredArgsConstructor
public abstract class LocalizedNumberConverter {

    // Desired locale for number converstion
    private final Locale locale;

    /**
     * Converts a Number to words.
     * Implementing classes must handle conversion into inherited locale.
     *
     * @param number Instance of Number
     * @return Word representation in language of inherited locale.
     */
    public abstract String convertToWords(Number number);


    /**
     * Gets the zero word of the specified languageCode.
     * @param languageCode String given by getLanguage() call of class's Locale.
     * @return zero word string
     */
    protected String getZeroWord(String languageCode) {
        return LanguageConstants.LANGUAGE_ZERO.get(languageCode);
    }

    /**
     * Gets the word for negative in the specified language
     * @param languageCode String given by getLanguage() call of class's Locale.
     * @return negative word string
     */
    protected String getNegativeWord(String languageCode) {
        return LanguageConstants.LANGUAGE_NEGATIVE.get(languageCode);
    }

    // Method is left abstract as different number types may have different ranges
    protected abstract String getMinValueWord(String langaugeCode);
}
