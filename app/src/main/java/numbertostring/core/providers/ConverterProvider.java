package numbertostring.core.providers;

import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LanguageRules;

/**
 * Generic interface for providing number converters based on language rules.
 */
@FunctionalInterface
public interface ConverterProvider {
    LocalizedNumberConverter createConverter(LanguageRules rules);
}
