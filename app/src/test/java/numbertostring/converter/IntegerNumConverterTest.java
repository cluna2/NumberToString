package numbertostring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import numbertostring.logger.GlobalLogger;
import numbertostring.pojo.IntegerNum;
import numbertostring.rules.DefaultLanguageRulesProvider;
import numbertostring.rules.LanguageRules;

public class IntegerNumConverterTest {
    
    private LanguageRules rules;

    private IntegerNumConverter converter;

    @BeforeEach
    void setUp() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.ENGLISH);
        converter = new IntegerNumConverter(rules);
    }



    @Test
    void testParsesIntMinValue_ENGLISH() {
        String expectedString = "Negative Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Eight";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MIN_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesIntMaxValue_ENGLISH() {
        String expectedString = "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MAX_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLongMaxValue_ENGLISH() {
        String expectedString = "Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Seven";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Long.MAX_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLongMinValue_ENGLISH() {
        String expectedString = "Negative Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Eight";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Long.MIN_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLongMaxValue_SPANISH() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.of("es", "ES"));
        converter = new IntegerNumConverter(rules);
        String expectedString = "Nueve Trillones Doscientos Veintitres Mil "
                               + "Trescientos Setenta Y Dos Billones "
                               + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                               + "Setecientos Setenta Y Cinco Mil Ochocientos Siete";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Long.MAX_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLongMinValue_SPANISH() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.of("es", "ES"));
        converter = new IntegerNumConverter(rules);
        String expectedString = "Menos Nueve Trillones Doscientos Veintitres Mil "
                               + "Trescientos Setenta Y Dos Billones "
                               + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                               + "Setecientos Setenta Y Cinco Mil Ochocientos Ocho";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Long.MIN_VALUE)).build();
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
    void testParsesIntMinValue_SPANISH() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.of("es", "ES"));
        converter = new IntegerNumConverter(rules);
        String expectedString = "Menos Dos Mil Ciento Cuarenta Y Siete Millones Cuatrocientos Ochenta Y Tres Mil Seiscientos Cuarenta Y Ocho";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MIN_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesIntMaxValue_SPANISH() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.of("es", "ES"));
        converter = new IntegerNumConverter(rules);
        String expectedString = "Dos Mil Ciento Cuarenta Y Siete Millones Cuatrocientos Ochenta Y Tres Mil Seiscientos Cuarenta Y Siete";
        IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf(Integer.MAX_VALUE)).build();
        String actualString = converter.convertToWords(num);
        assertEquals(expectedString, actualString);
    }

    @Test
    void testParsesLargeUnits() {
        List<String> expectedStrings = new ArrayList<>(Arrays.asList(
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
            "One Trillion"
        ));
            testLargeUnitStrings(expectedStrings);
    }

    @Test
    void testParseSpanishLargeUnits() {
        rules = new DefaultLanguageRulesProvider().getLanguageRules(Locale.of("es", "ES"));
        converter = new IntegerNumConverter(rules);
        List<String> expectedStrings = new ArrayList<>(Arrays.asList(
            "Uno", 
            "Diez", 
            "Cien", 
            "Mil", 
            "Diez Mil", 
            "Cien Mil", 
            "Un Millon", 
            "Diez Millones", 
            "Cien Millones", 
            "Mil Millones", 
            "Diez Mil Millones", 
            "Cien Mil Millones",
            "Un Billon",
            "Diez Billones",
            "Cien Billones",
            "Mil Billones"
        ));

        testLargeUnitStrings(expectedStrings);
    }

    void testLargeUnitStrings(List<String> expectedStrings) {
        List<String> actualStrings = new ArrayList<>();
        for (int i = 0; i < expectedStrings.size(); i++) {
            IntegerNum num = IntegerNum.builder().value(BigInteger.valueOf((long)Math.pow(10, i))).build();
            String actualString = converter.convertToWords(num);
            actualStrings.add(actualString);
        }

        for (int i = 0; i < actualStrings.size(); i++) {
            GlobalLogger.LOGGER.debug(
                String.format("Exepected: %s, Actual: %s",
                    expectedStrings.get(i), actualStrings.get(i)));
            assertEquals(expectedStrings.get(i), actualStrings.get(i));
        }
    }
    
}
