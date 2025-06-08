package numbertostring.unit.core.language.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ibm.icu.text.RuleBasedNumberFormat;

import numbertostring.core.language.rules.SpanishNumeralRules;

public class SpanishNumeralRulesTest {
    
    private final SpanishNumeralRules rules = new SpanishNumeralRules();





    // ICU4J Rule-Based Formatter
    private static final RuleBasedNumberFormat formatter =
        new RuleBasedNumberFormat(Locale.of("es", "ES"), RuleBasedNumberFormat.SPELLOUT);

    // Generate expected test cases using ICU4J
    private static Stream<Arguments> provideSpanishNumerals() {
        return IntStream.rangeClosed(0, 1000) // Adjust range as needed
                       .mapToObj(n -> Arguments.of(n, applyOverrides(formatter.format(n))));
    }

    // Method to "undo" grammar rules and formatting to retrieve raw numerals 
    private static String applyOverrides(String formatted) {
        return formatted = formatted.replaceAll("\\bveinti(\\w+)\\b", "veinte $1") // "veintiuno" → "veinte uno"
                            .replaceAll("\\bciento\\b", "cien") // "ciento uno" -> "cien uno"
                            .replaceAll("\\b[yY]\\b", "") // "Cuarenta y cuatro" -> "Cuarenta cuatro"
                            .replaceAll("á", "a") // remove accents
                            .replaceAll("é", "e")
                            .replaceAll("í", "i")
                            .replaceAll("ó", "o")
                            .replaceAll("\\s+", " ");
    }

    @ParameterizedTest
    @MethodSource("provideSpanishNumerals")
    void testSpanishNumeralConversion(int num, String expectedOutput) {
        assertEquals(expectedOutput, rules.applyNumeralRulesForSmallNumbers(num).toLowerCase());
    }
}
