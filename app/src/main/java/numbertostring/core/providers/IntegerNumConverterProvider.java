package numbertostring.core.providers;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LanguageRules;


public class IntegerNumConverterProvider implements ConverterProvider {
    @Override
    public LocalizedNumberConverter createConverter(LanguageRules rules) {
        return new IntegerNumConverter(rules);
    }
}
