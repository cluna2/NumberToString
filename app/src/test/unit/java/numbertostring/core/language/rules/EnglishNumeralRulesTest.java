package numbertostring.unit.core.language.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import numbertostring.core.language.rules.EnglishNumeralRules;

public class EnglishNumeralRulesTest {
    private static final EnglishNumeralRules rules = new EnglishNumeralRules();


    // Helper method to generate a mapping from integers to raw converted numerals before formatting rules apply.
    private static Stream<Arguments> provideEnglishNumerals() {
        Map<Integer, String> englishUnformattedNumerals = new HashMap<>(Map.ofEntries(
            Map.entry(1, "One"), Map.entry(2, "Two"), Map.entry(3, "Three"),
            Map.entry(4, "Four"), Map.entry(5, "Five"), Map.entry(6, "Six"), Map.entry(7, "Seven"),
            Map.entry(8, "Eight"), Map.entry(9, "Nine"), Map.entry(10, "Ten"),
            Map.entry(11, "Eleven"), Map.entry(12, "Twelve"), Map.entry(13, "Thirteen"), Map.entry(14, "Fourteen"),
            Map.entry(15, "Fifteen"), Map.entry(16, "Sixteen"), Map.entry(17, "Seventeen"), Map.entry(18, "Eighteen"),
            Map.entry(19, "Nineteen"), Map.entry(20, "Twenty"), Map.entry(30, "Thirty"), Map.entry(40, "Forty"),
            Map.entry(50, "Fifty"), Map.entry(60, "Sixty"), Map.entry(70, "Seventy"), Map.entry(80, "Eighty"),
            Map.entry(90, "Ninety"), Map.entry(100, "One")
        ));

        // Generate numbers from 21 - 99, excluding numbers mapped above
        for (int i = 2; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                englishUnformattedNumerals.putIfAbsent(10 * i + j, 
                    englishUnformattedNumerals.get(10 * i) + " " + englishUnformattedNumerals.get(j));
            }

        }

        
        // Generate numbers from 101 - 999
        for (int i = 1; i <= 9; i++) { // Hundreds place
            englishUnformattedNumerals.putIfAbsent(100 * i, englishUnformattedNumerals.get(i)); // Generate each new hundred
            for (int j = 1; j < 100; j++) { // Append two-digit words
                englishUnformattedNumerals.putIfAbsent(100 * i + j, 
                    englishUnformattedNumerals.get(i)  + " " + englishUnformattedNumerals.get(j));
            }
        }

        return IntStream.rangeClosed(1, 999)
            .mapToObj(n -> Arguments.of(n, englishUnformattedNumerals.get(n)));

    }


    @ParameterizedTest
    @MethodSource("provideEnglishNumerals")
    void testEnglishNumeralConversionUpToThousand(int num, String expectedOutput) {
        assertEquals(expectedOutput, rules.applyNumeralRulesForSmallNumbers(num));
    }

}