package numbertostring.rules;

import java.util.Locale;

import numbertostring.language.EnglishNumeralRules;
import numbertostring.language.SpanishNumeralRules;
import numbertostring.logger.GlobalLogger;

/**
 * Implementation for LanguageRulesProvider. Responsible for creating a LanguageRules object.
 */
public class DefaultLanguageRulesProvider implements LanguageRulesProvider {

    /** Default constructor. */
    public DefaultLanguageRulesProvider() {}
    
    /**
     * Returns a LanguageRules object depending on the Locale provided.
     * For locales with non-positional number systems, a function for custom rules support is used
     * and other entries are left null.
     * @param locale locale to create rules object for
     */
    @Override
    public LanguageRules getLanguageRules(Locale locale) {
        GlobalLogger.LOGGER.debug(String.format("Looking for rules from given locale: %s", locale.toString()));
        return switch (locale.getLanguage()) {
            case "en" -> new LanguageRules(
                EnglishNumeralRules.SCALE_TYPE,
                EnglishNumeralRules.NEGATIVE_WORD,
                EnglishNumeralRules.NUMERALS,
                EnglishNumeralRules.LARGE_UNITS,
                EnglishNumeralRules.SMALL_NUMERAL_LOGIC,
                EnglishNumeralRules.LARGE_UNITS_LOGIC

            );

            case "es" -> new LanguageRules(
                SpanishNumeralRules.SCALE_TYPE,
                SpanishNumeralRules.NEGATIVE_WORD,
                SpanishNumeralRules.NUMERALS,
                SpanishNumeralRules.LARGE_UNITS,
                SpanishNumeralRules.SMALL_NUMERAL_LOGIC,
                SpanishNumeralRules.LARGE_UNITS_LOGIC
            );
            default -> null;
        };
    }
}
