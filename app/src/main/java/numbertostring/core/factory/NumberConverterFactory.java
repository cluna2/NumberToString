package numbertostring.core.factory;

import numbertostring.api.exception.UnsupportedLanguageException;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LanguageRules;
import numbertostring.core.language.rules.LanguageRulesRegistry;
import numbertostring.core.model.IntegerNum;
import numbertostring.core.model.Number;
import numbertostring.core.providers.ConverterProvider;
import numbertostring.core.utils.logger.GlobalLogger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Factory responsible for retrieving the correct {@link LocalizedNumberConverter} based on
 * registered {@link ConverterProvider} implementations.
 *
 * <p>This factory determines the appropriate number type and retrieves the corresponding
 * converter for transforming numeric values into their word representation.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * NumberConverterFactory factory = new NumberConverterFactory();
 * factory.registerConverter(IntegerNum.class, new IntegerNumConverterProvider());
 *
 * LocalizedNumberConverter converter = factory.getConverterForNumber(new BigDecimal("1000"), Locale.ENGLISH);
 * String words = converter.convertToWords(new IntegerNum(new BigDecimal("1000").toBigInteger()));
 * System.out.println(words); // Output: "One Thousand"
 * </pre>
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Maintains a registry of number types and their respective converters.</li>
 *   <li>Dynamically detects the appropriate {@code Number<?>} type for conversion.</li>
 *   <li>Supports localization by adapting conversions to different language rules.</li>
 * </ul>
 */
public class NumberConverterFactory {
    private static final Map<Class<? extends Number<?>>, ConverterProvider> converterRegistry = new HashMap<>();

    /**
     * Registers a {@link ConverterProvider} for a specific number type.
     *
     * @param numberType The class representing the number type (e.g., {@code IntegerNum.class}).
     * @param provider The provider responsible for creating converters for this number type.
     */
    public <T extends Number<T>> void registerConverter(Class<T> numberType, ConverterProvider provider) {
        converterRegistry.put(numberType, provider);
    }

    /**
     * Converts a numeric value into its word representation based on locale.
     *
     * <p>This method automatically determines the appropriate {@link Number} type,
     * retrieves the corresponding {@link LocalizedNumberConverter}, and performs
     * the conversion.</p>
     *
     * <p><strong>Features:</strong></p>
     * <ul>
     *   <li>Detects the correct number type dynamically</li>
     *   <li>Supports localization by adapting conversions to different language rules</li>
     * </ul>
     *
     * @param numberValue The numeric value to be converted.
     * @param locale The locale determining the linguistic rules for conversion.
     * @return The word representation of the numeric value.
     * @throws UnsupportedLanguageException If the locale is not supported.
     * @throws IllegalArgumentException If the numeric value is null or unsupported.
     */
    public String convertNumberToWords(BigDecimal numberValue, Locale locale) {
        Number<?> numberInstance = detectNumberType(numberValue);

        LocalizedNumberConverter converter = getConverterForNumber(numberValue, locale);
        return converter.convertToWords(numberInstance);
    }

    /**
     * Retrieves the appropriate {@link LocalizedNumberConverter} for a given number and locale.
     *
     * @param numberValue The numeric value to be converted.
     * @param locale The locale that determines the language-specific conversion rules.
     * @return An instance of {@link LocalizedNumberConverter} suitable for the given number type.
     * @throws UnsupportedLanguageException If the locale is not supported.
     * @throws IllegalArugmentException If the number type could not be determined.
     */
    private LocalizedNumberConverter getConverterForNumber(BigDecimal numberValue, Locale locale) {
        LanguageRules rules = getLanguageRulesFromLocale(locale);
        Number<?> numberInstance = detectNumberType(numberValue);

        ConverterProvider provider = converterRegistry.get(numberInstance.getClass());
        if (provider == null) {
            throw new IllegalArgumentException("No converter registered for type: " + numberInstance.getClass().getSimpleName());
        }

        GlobalLogger.LOGGER.debug(String.format("Creating converter for %s type.", numberInstance.getClass().getSimpleName()));
        return provider.createConverter(rules);
    }



    /** Determines the correct Number<?> type based on numeric properties. */
    private Number<?> detectNumberType(BigDecimal numberValue) {
        if (numberValue.scale() == 0 || numberValue.stripTrailingZeros().scale() <= 0) {
            return new IntegerNum(numberValue.toBigInteger()); // Whole number → IntegerNum
        }
        throw new IllegalArgumentException("Unsupported number type.");
    }

    /** Fetches language-specific numerical rules from locale. */
    private LanguageRules getLanguageRulesFromLocale(Locale locale) {
        LanguageRules rules = LanguageRulesRegistry.getRules(locale);
        if (rules == null) {
            throw new UnsupportedLanguageException("Language not yet supported.");
        }
        return rules;
    }
}

