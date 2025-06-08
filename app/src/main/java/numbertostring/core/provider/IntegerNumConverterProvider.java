package numbertostring.core.provider;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.formatting.LocalizedGrammarFormatter;
import numbertostring.core.language.rules.LocalizedNumeralRules;


public class IntegerNumConverterProvider implements ConverterProvider {
    @Override
    public LocalizedNumberConverter createConverter(
        LocalizedNumeralRules rules, LocalizedGrammarFormatter formatter) {
        return new IntegerNumConverter(rules, formatter);
    }
}
