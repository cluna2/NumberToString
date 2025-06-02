package numbertostring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import numbertostring.language.DefaultLanguageRulesProvider;
import numbertostring.language.LanguageRules;
import numbertostring.pojo.IntegerNum;

public class IntegerNumConverterTest {
    

    private LanguageRules rules;

    private IntegerNumConverter converter;

    @BeforeEach
    void setUp() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.ENGLISH);
        converter = new IntegerNumConverter(rules);
    }



    @Test
    void testParsesIntMinValue() {
        String expectedString = "Negative Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Eight";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MIN_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesIntMaxValue() {
        String expectedString = "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MAX_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesZeroValue() {

        String expectedString = "Zero";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(0)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLargeUnits() {
        List<String> expectedStrings = Arrays.asList(new String[]{
            "One",
            "Ten",
            "One Hundred",
            "One Thousand",
            "Ten Thousand",
            "One Hundred Thousand",
            "One Million",
            "Ten Million",
            "One Hundred Million",
            "One Billion",
            "Ten Billion",
            "One Hundred Billion",
            "One Trillion"});
        List<String> actualStrings = new ArrayList<>();
        for (int i = 0; i < expectedStrings.size(); i++) {
            IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf((long)Math.pow(10, i))).build();
            String actualString = converter.convertToWords(num);
            actualStrings.add(actualString);
            assertEquals(expectedStrings.get(i), actualString);
        }
    }

}
