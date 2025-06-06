package numbertostring.core.providers;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LocalizedNumberRules;


public class IntegerNumConverterProvider implements ConverterProvider {
    @Override
    public LocalizedNumberConverter createConverter(LocalizedNumberRules rules) {
        return new IntegerNumConverter(rules);
    }
}
