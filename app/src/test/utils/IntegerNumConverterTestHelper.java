package numbertostring.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.LocalizedGrammarFormatterRegistry;
import numbertostring.core.language.LocalizedNumberRulesRegistry;
import numbertostring.core.language.formatting.LocalizedGrammarFormatter;
import numbertostring.core.language.rules.LocalizedNumeralRules;
import numbertostring.core.model.IntegerNum;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerNumConverterTestHelper {
    
    public static IntegerNumConverter createConverter(Locale locale) {
        LocalizedNumeralRules rules = LocalizedNumberRulesRegistry.getRules(locale);
        LocalizedGrammarFormatter formatter = LocalizedGrammarFormatterRegistry.getFormatter(locale);
        return new IntegerNumConverter(rules, formatter);
    }

    public static IntegerNum createNumber(BigInteger value) {
        return IntegerNum.builder().value(value).build();
    }

    public static void assertConversion(IntegerNumConverter converter, BigInteger value, String expectedText) {
        String actualString = converter.convertToWords(createNumber(value));
        assertEquals(expectedText, actualString);
    }
    
    public static void assertConversionList(IntegerNumConverter converter, List<BigInteger> values, List<String> expectedTexts) {
        List<String> actualTexts = values.stream()
            .map(IntegerNumConverterTestHelper::createNumber)
            .map(converter::convertToWords)
            .collect(Collectors.toList());
        values.stream().
            forEach(i -> assertEquals(expectedTexts.get(values.indexOf(i)), actualTexts.get(values.indexOf(i))));
    }
}

