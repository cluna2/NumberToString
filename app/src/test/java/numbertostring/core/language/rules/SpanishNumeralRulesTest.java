package numbertostring.core.language.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SpanishNumeralRulesTest {
    
    private final SpanishNumeralRules rules = new SpanishNumeralRules();

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
        BigInteger num = new BigInteger(input);
        assertEquals(expectedOutput, rules.processNumber(num));
    }

    @Test
    void testParsesZero() {
        assertEquals("Cero", rules.applyNumeralRulesForZero());
    }

    @Test
    void testParsesLongMaxValue() {
        String expectedOutput = "Nueve Trillones Doscientos Veintitres Mil "
                               + "Trescientos Setenta Y Dos Billones "
                               + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                               + "Setecientos Setenta Y Cinco Mil Ochocientos Siete";
        BigInteger num = BigInteger.valueOf(Long.MAX_VALUE);
        assertEquals(expectedOutput, rules.processNumber(num));
    }


    @Test
    void testParsesLongMinValue() {
        String expectedOutput = "Menos Nueve Trillones Doscientos Veintitres Mil "
                                + "Trescientos Setenta Y Dos Billones "
                                + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                                + "Setecientos Setenta Y Cinco Mil Ochocientos Ocho";
        // Each rules object assumes non-negative input. IntegerNumConverter handles negative cases.
        BigInteger num = BigInteger.valueOf(Long.MIN_VALUE).negate();
        assertEquals(expectedOutput, "Menos " + rules.processNumber(num));
    }
}
