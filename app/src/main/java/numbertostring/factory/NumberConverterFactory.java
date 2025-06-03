package numbertostring.factory;

import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.exception.UnsupportedLanguageException;
import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.language.LanguageRules;
import numbertostring.logger.GlobalLogger;
import numbertostring.pojo.Number;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
     * @param numberType Class of the number's type T.
     * @param <T> Type of the number.
     * @param locale Locale for language conversion
     * @return LocalizedNumberConverter of the same type as Number
     * @throws ReflectiveOperationException If the converter for a registered type fails.
     */
    public <T extends Number<T>> LocalizedNumberConverter<T> getConverterForType(Class<T> numberType, Locale locale) 
        throws ReflectiveOperationException {
        LanguageRules rules = getLanguageRulesFromLocale(locale);
        GlobalLogger.LOGGER.info(
            String.format("Language specific rules found for your locale", locale.toString()));

        try {
            GlobalLogger.LOGGER.debug(
                String.format("Attempting to instantiate %s number type.", numberType.toString()));
            T instance = numberType.getDeclaredConstructor(BigDecimal.class).newInstance(BigDecimal.ZERO);

            GlobalLogger.LOGGER.debug(
                String.format("Attempting to create converter for %s number type.", numberType.toString()));
                
            LocalizedNumberConverter<T> converter = instance.getConverter(rules);
            GlobalLogger.LOGGER.debug(
                String.format("Converter creation successful for %s number type.", numberType.toString()));
            return converter;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectiveOperationException("Cannot instantiate number type: " + numberType.getName(), e);
        }
    }

    /**
     * Fetches the numerical language constants and rules from the given locale or throws an exception if the 
     * language is unsupported.
     * @param locale Locale for language conversion.
     * @return Language rules for specified locale.
     */
    private LanguageRules getLanguageRulesFromLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null.");
        }
        LanguageRules languageRules = new DefaultLanguageRulesProvider().getLanguageRules(locale);
        GlobalLogger.LOGGER.debug(
                String.format("Language rules extracted."));
        if (languageRules == null) {
            GlobalLogger.LOGGER.error(
                String.format("Language rules are null. Language is not supported."));
            throw new UnsupportedLanguageException("Language not yet supported.");
        }
        return languageRules;
            
    }
}
