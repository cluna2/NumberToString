package numbertostring.api;

import numbertostring.converter.IntegerNumConverter;
import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.pojo.IntegerNum;
import numbertostring.pojo.Number;
import java.util.Locale;

public class NumberToWordsAPI {

    /**
     * Converts any Number type to English word form string representation.
     * Use convertNumberToWordsWithLocale to specify locale.
     * @param number Instance of Number
     * @return Word representation in English by default.
     */
    public static String convertNumberToWords(Number number) {
        return convertNumberToWordsWithLocale(number, Locale.ENGLISH);
    }

    /**
     * Converts any Number type to word form of specified locale.
     *
     * @param number Instance of Number
     * @param locale Locale for language conversion
     * @return Word representation in the given language
     */
    public static String convertNumberToWordsWithLocale(Number number, Locale locale) {
        LocalizedNumberConverter converter = getConverterForType(number, locale);
        return converter.convertToWords(number);
    }

    /*
     * Creates a number convertor of the same Number type as the given number.
     * @param number instance of Number
     * @param Locale locale for language conversion
     * @return LocalizedNumberConverter of same type as Number type
     */
    private static LocalizedNumberConverter getConverterForType(Number number, Locale locale) {
        if (number instanceof IntegerNum) {
            return new IntegerNumConverter(locale);
        }
        throw new UnsupportedOperationException("Number type not yet supported.");
    }
}