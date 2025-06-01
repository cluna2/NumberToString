package numbertostring.factory;

import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.exception.UnsupportedLanguageException;
import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.language.LanguageRules;
import numbertostring.pojo.Number;
import java.util.Locale;

/**
 * Factory class responsible for creating instances of LocalizedNumberConverter{@literal <T>} 
 * based on the type of Number{@literal <T>} provided. Ensures the appropriate converter is returned
 * for a provided Number type.
 */
public class NumberConverterFactory {

    /** Basic constructor for a NumberConverterFactory. */
    public NumberConverterFactory() {}


    /**
     * Creates a localized number converter for the given number.
     * @param number Instance of Number
     * @param <T> Type of the number.
     * @param locale Locale for language conversion
     * @return LocalizedNumberConverter of the same type as Number
     * @throws UnsupportedOperationException If the Number type is not registered in the factory.
     * @throws RuntimeException If the converter for a registered type fails.
     */
    public <T extends Number<T>> LocalizedNumberConverter<T> getConverterForType(T number, Locale locale) {
        LanguageRules rules = getLanguageRulesFromLocale(locale);
        return number.getConverter(rules);
    }

    /**
     * Fetches the numerical language constants and rules from the given locale or throws an exception if the 
     * language is unsupported.
     * @param locale Locale for language conversion
     * @return language rules for specified locale
     */
    private LanguageRules getLanguageRulesFromLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null.");
        }
        LanguageRules languageRules = new DefaultLanguageRulesProvider().getLanguageRules(locale);
        if (languageRules == null) {
            throw new UnsupportedLanguageException("Language not yet supported.");
        }
        return languageRules;
            
    }
}
