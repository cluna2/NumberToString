package numbertostring.factory;

import java.util.Map;

import numbertostring.converter.IntegerNumConverter;
import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.language.LanguageRules;
import numbertostring.pojo.Number;
import numbertostring.pojo.IntegerNum;

import java.util.HashMap;
import java.util.Locale;

/**
 * Factory class responsible for creating instances of LocalizedNumberConverter{@literal <T>} 
 * based on the type of Number{@literal <T>} provided.
 */
public class NumberConverterFactory {
    private static final Map<Class<?>, Class<? extends LocalizedNumberConverter<?>>> CONVERTER_MAP = new HashMap<>();

    static {
        CONVERTER_MAP.put(IntegerNum.class, IntegerNumConverter.class);
    }

    /**
     * Creates a localized number converter for the given number.
     * @param number Instance of Number
     * @param locale Locale for language conversion
     * @return LocalizedNumberConverter of the same type as Number
     * @throws UnsupportedOperationException If the Number type is not registered in the factory.
     * @throws RuntimeException If the converter for a registered type fails.
     */
    public static <T extends Number<T>> LocalizedNumberConverter<T> getConverterForType(T number, Locale locale) {
        LanguageRules rules = getLanguageRulesFromLocale(locale);
        Class<?> type = number.getType();

        if (CONVERTER_MAP.containsKey(type)) {
            try {
                return (LocalizedNumberConverter<T>) CONVERTER_MAP.get(type)
                    .getDeclaredConstructor(LanguageRules.class)
                    .newInstance(rules);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create converter for type: " + type.getSimpleName(), e);
            }
        } else {
            throw new UnsupportedOperationException("Number type not yet supported: " + type.getSimpleName());
        }
    }

    /**
     * Fetches the numerical language constants and rules from the given locale or throws an exception if the 
     * language is unsupported.
     * @param locale Locale for language conversion
     * @return language rules for specified locale
     */
    private static LanguageRules getLanguageRulesFromLocale(Locale locale) {
        LanguageRules languageRules = new DefaultLanguageRulesProvider().getLanguageRules(locale);
        if (languageRules == null) {
            throw new UnsupportedOperationException("Language not yet supported.");
        }
        return languageRules;
            
    }
}
