package numbertostring.api;

import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.factory.NumberConverterFactory;
import numbertostring.pojo.Number;
import java.util.Locale;

/*
 * Consumers can call the APIs specified here to convert a number to a string
 */
public class NumberToWordsAPI {

    /**
     * Converts any Number type to English word form string representation.
     * Use convertNumberToWordsWithLocale to specify locale.
     * @param number Instance of Number
     * @param <T> Type of number
     * @return Word representation in English by default.
     */
    public static <T extends Number<T>> String convertNumberToWords(T number) {
        return convertNumberToWordsWithLocale(number, Locale.ENGLISH);
    }

    /**
     * Converts any Number type to word form of specified locale.
     *
     * @param number Instance of Number
     * @param locale Locale for language conversion
     * @param <T> Type of number
     * @return Word representation in the given language
     */
    public static <T extends Number<T>> String convertNumberToWordsWithLocale(T number, Locale locale) {
        LocalizedNumberConverter<T> converter;
        try {
            converter = NumberConverterFactory.getConverterForType(number, locale);
            return converter.convertToWords(number);
        } catch (UnsupportedOperationException e) {
            System.out.printf("Error creating covnerter: %s", e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.printf("Error converting number: %s", e.getMessage());
        }
        return "";
    }
}