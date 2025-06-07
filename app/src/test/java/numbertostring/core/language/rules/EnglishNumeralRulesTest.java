package numbertostring.core.language.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EnglishNumeralRulesTest {
    private final EnglishNumeralRules rules = new EnglishNumeralRules();

    @ParameterizedTest
    @CsvSource({
        "1, One",
        "15, Fifteen",
        "100, One Hundred",
        "1000, One Thousand",
        "1000000, One Million",
        "123456, One Hundred Twenty Three Thousand Four Hundred Fifty Six"
    })
    void testNumeralConversion(String input, String expectedOutput) {
        BigInteger num = new BigInteger(input);
        assertEquals(expectedOutput, rules.processNumber(num));
    }

    @Test
    void testChunkProcessing() {
        assertEquals("One Hundred Twenty Three Thousand Four Hundred Fifty Six",
            rules.processNumber(BigInteger.valueOf(123456)));
        assertEquals("Ten Thousand", rules.processNumber(BigInteger.valueOf(10_000)));
        assertEquals("One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety",
            rules.processNumber(BigInteger.valueOf(1_234_567_890)));
    }


    @Test
    void testParsesLongMaxValue() {
        String expectedOutput = "Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Seven";
        BigInteger num = BigInteger.valueOf(Long.MAX_VALUE);
        assertEquals(expectedOutput, rules.processNumber(num));
    }

    @Test
    void testParsesLongMinValue() {
        String expectedOutput = "Negative Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Eight";
        // Each rules object assumes non-negative input. IntegerNumConverter handles negative cases.
        BigInteger num = BigInteger.valueOf(Long.MIN_VALUE).negate();
        assertEquals(expectedOutput, "Negative " + rules.processNumber(num));
    }

}