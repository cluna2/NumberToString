package numbertostring.core.provider;

import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.formatting.LocalizedGrammarFormatter;
import numbertostring.core.language.rules.LocalizedNumeralRules;

/**
 * Generic interface for providing number converters based on language rules.
 */
@FunctionalInterface
public interface ConverterProvider {
    LocalizedNumberConverter createConverter(
        LocalizedNumeralRules rules, LocalizedGrammarFormatter formatter);
}
