package numbertostring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ibm.icu.text.RuleBasedNumberFormat;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.formatting.SpanishGrammarFormatter;
import numbertostring.core.language.rules.SpanishNumeralRules;
import numbertostring.core.model.IntegerNum;


public class SpanishNumberConversionIntegrationTest {
    
    
        
    private static final SpanishNumeralRules rules = new SpanishNumeralRules();
    private static final SpanishGrammarFormatter formatter = new SpanishGrammarFormatter();
    private static final IntegerNumConverter converter =  new IntegerNumConverter(rules, formatter);

    private IntegerNum num;


    @ParameterizedTest
    @CsvSource({
            "1, Uno", 
            "10, Diez", 
            "100, Cien", 
            "1000, Mil", 
            "10000, Diez Mil", 
            "100000, Cien Mil", 
            "1000000, Un Millon", 
            "10000000, Diez Millones", 
            "100000000, Cien Millones", 
            "1000000000, Mil Millones", 
            "10000000000, Diez Mil Millones", 
            "100000000000, Cien Mil Millones",
            "1000000000000, Un Billon",
            "10000000000000, Diez Billones",
            "100000000000000, Cien Billones",
            "1000000000000000, Mil Billones"
    })
    void testParsesLargeUnits(String input, String expectedOutput) {
        num = new IntegerNum(new BigInteger(input));
        String actualOutput = converter.convertToWords(num);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testParsesZero() {
        assertEquals("Cero", rules.applyNumeralRulesForZero());
    }

    @Test
    void testParsesLongMaxValue() {
        num = new IntegerNum(BigInteger.valueOf(Long.MAX_VALUE));
        String expectedOutput = "Nueve Trillones Doscientos Veintitrés Mil " +
            "Trescientos Setenta y Dos Billones " +
            "Treinta y Seis Mil Ochocientos Cincuenta y Cuatro Millones " +
            "Setecientos Setenta y Cinco Mil Ochocientos Siete";
        
        assertEquals(expectedOutput, converter.convertToWords(num));
    }


    @Test
    void testParsesLongMinValue() {
        num = new IntegerNum(BigInteger.valueOf(Long.MIN_VALUE));
        String expectedOutput = 
            "Menos Nueve Trillones Doscientos Veintitrés Mil " +
            "Trescientos Setenta y Dos Billones " +
            "Treinta y Seis Mil Ochocientos Cincuenta y Cuatro Millones " +
            "Setecientos Setenta y Cinco Mil Ochocientos Ocho";
        assertEquals(expectedOutput, converter.convertToWords(num));
    }
}
    
