package numbertostring.language;

import java.util.Locale;
import numbertostring.constants.EnglishNumeralConstants;
import numbertostring.logger.GlobalLogger;

/**
 * Implementation for LanguageRulesProvider. Creates a LanguageRules object for given Locale.
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
                EnglishNumeralConstants.NUMERALS,
                EnglishNumeralConstants.LARGE_UNITS,
                EnglishNumeralConstants.BASE_UNITS,
                EnglishNumeralConstants.GROUPING,
                EnglishNumeralConstants.EXPLICIT_ONE,
                EnglishNumeralConstants.NEGATIVE_WORD,
                null
            );
            default -> null;
        };
    }
}
