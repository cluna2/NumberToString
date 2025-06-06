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
        assertEquals(expectedOutput, rules.processNumberChunks(num));
    }

    @Test
    void testChunkProcessing() {
        assertEquals("One Hundred Twenty Three Thousand Four Hundred Fifty Six",
            rules.processNumberChunks(BigInteger.valueOf(123456)));
        assertEquals("Ten Thousand", rules.processNumberChunks(BigInteger.valueOf(10_000)));
        assertEquals("One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety",
            rules.processNumberChunks(BigInteger.valueOf(1_234_567_890)));
    }
}