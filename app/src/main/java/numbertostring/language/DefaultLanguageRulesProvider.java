package numbertostring.language;

import java.util.Locale;
import numbertostring.constants.EnglishNumeralConstants;

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
     * @throws IllegalArgumentException if locale is unsupported
     */
    @Override
    public LanguageRules getLanguageRules(Locale locale) {
        return switch (locale.getLanguage()) {
            case "en" -> new LanguageRules(
                EnglishNumeralConstants.NUMERALS,
                EnglishNumeralConstants.LARGE_UNITS,
                EnglishNumeralConstants.USES_THOUSANDS_GROUPING,
                EnglishNumeralConstants.NEGATIVE_WORD,
                null
            );
            default -> null;
        };
    }
}
