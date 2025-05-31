package numbertostring.language;

import java.util.Locale;
import numbertostring.constants.EnglishNumeralConstants;

public class DefaultLanguageRulesProvider implements LanguageRulesProvider {
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
            default -> throw new IllegalArgumentException("Unsupported locale: " + locale);
        };
    }
}
