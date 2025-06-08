package numbertostring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.formatting.EnglishGrammarFormatter;
import numbertostring.core.language.rules.EnglishNumeralRules;
import numbertostring.core.model.IntegerNum;

public class EnglishNumberConversionIntegrationTest {

    private static final EnglishNumeralRules rules = new EnglishNumeralRules();
    private static final EnglishGrammarFormatter formatter = new EnglishGrammarFormatter();
    private static final IntegerNumConverter converter = new IntegerNumConverter(rules, formatter);

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
        String actualOutput = converter.convertToWords(new IntegerNum(num));
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void testParsesLongMaxValue() {
        String expectedOutput = "Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Seven";
        BigInteger num = BigInteger.valueOf(Long.MAX_VALUE);
        String actualOutput = converter.convertToWords(new IntegerNum(num));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testParsesLongMinValue() {
        String expectedOutput = "Negative Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Eight";

        BigInteger num = BigInteger.valueOf(Long.MIN_VALUE);
        String actualOutput = converter.convertToWords(new IntegerNum(num));
        assertEquals(expectedOutput, actualOutput);
    }
}
