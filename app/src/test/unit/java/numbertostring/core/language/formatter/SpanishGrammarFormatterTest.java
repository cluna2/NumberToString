package numbertostring.unit.core.language.formatter;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import numbertostring.core.language.formatting.SpanishGrammarFormatter;

public class SpanishGrammarFormatterTest {

    private final SpanishGrammarFormatter formatter = new SpanishGrammarFormatter();

    
    @Test
    void testProperHundredUsage() {
        String chunkString = "Cien Uno";
        BigInteger chunk = BigInteger.valueOf(101);
        String unitString = "";
        BigInteger unit = BigInteger.ONE;

        String actualOutput = formatter.applyLanguageSpecificFormatting(chunkString, chunk, unitString, unit);
        assertEquals("Ciento Uno", actualOutput);
    }

    @Test
    void testOneThousandException() {
        String chunkString = "Uno";
        BigInteger chunk = BigInteger.valueOf(1);
        String unitString = "Mil";
        BigInteger unit = BigInteger.valueOf(1000);

        String actualOutput = formatter.applyLanguageSpecificFormatting(chunkString, chunk, unitString, unit);
        assertEquals("Mil", actualOutput);
    }

    @ParameterizedTest
    @CsvSource({
        "Millones, 1000000, Un Millon",
        "Billones, 1000000000000, Un Billon",
        "Trillones, 1000000000000000000, Un Trillon",
        "Cuatrillones, 1000000000000000000000000, Un Cuatrillon"
    })
    void testMasculineFormOfOneUsed(String unitString, String inputUnit, String expectedOutput) {
        String chunkString = "Uno";
        BigInteger chunk = BigInteger.valueOf(1);

        
        BigInteger unit = new BigInteger(inputUnit);

        String actualOutput = formatter.applyLanguageSpecificFormatting(chunkString, chunk, unitString, unit);
        assertEquals(expectedOutput, actualOutput);
    }

    @ParameterizedTest
    @MethodSource("provideDoubleDigitArguments")
    void testTensAndOnesFormatting(int doubleDigitNum, String chunkString, String expectedOutput) {
        BigInteger chunk = BigInteger.valueOf(doubleDigitNum);
        String unitString = "";
        BigInteger unit = BigInteger.valueOf(1);

        String actualOutput = formatter.applyLanguageSpecificFormatting(chunkString, chunk, unitString, unit);
        assertEquals(expectedOutput, actualOutput);
    }



    // Helper method to generate raw, incorrectly formatted numerals from 10-99 before formatting
    // Also produces  numerals after formatting.
    private static Stream<Arguments> provideDoubleDigitArguments() {
        Map<Integer, String> inputNumberMap = new HashMap<>();
        inputNumberMap.put(10, "Diez"); inputNumberMap.put(11, "Once"); inputNumberMap.put(12, "Doce");
        inputNumberMap.put(13, "Trece"); inputNumberMap.put(14, "Catorce"); inputNumberMap.put(15, "Quince");
        inputNumberMap.put(16, "Dieciséis"); inputNumberMap.put(17, "Diecisiete"); inputNumberMap.put(18, "Dieciocho");
        inputNumberMap.put(19, "Diecinueve"); inputNumberMap.put(20, "Veinte");
        
        Map<Integer, String> outputNumberMap = new HashMap<>(inputNumberMap);
        // Numbers 21-99 follow a pattern
        String[] tens = {"Veinti", "Treinta", "Cuarenta", "Cincuenta", "Sesenta", "Setenta", "Ochenta", "Noventa"};
        String[] units = {"Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve"};
        String[] accentedUnits = {"", "dós", "trés", "", "", "séis", "", "", ""};

        for (int i = 1; i <= 9; i++) {
            inputNumberMap.put(20 + i, "Veinte " + units[i - 1]);
            // Apply accents for 22, 23, and 26
            if (i == 2 || i == 3 || i == 6) {
                String accentedString = "Veinti" + accentedUnits[i - 1];
                outputNumberMap.put(20 + i, accentedString);
            } else {
                outputNumberMap.put(20 + i, "Veinti" + units[i - 1].toLowerCase());
            }
        } 

        for (int i = 3; i <= 9; i++) {
            inputNumberMap.put(i * 10, tens[i - 2]); // Tens directly mapped
            outputNumberMap.put(i * 10, tens[i - 2]);
            for (int j = 1; j <= 9; j++) {
                inputNumberMap.put(i * 10 + j, tens[i - 2] + " " + units[j - 1]);
                outputNumberMap.put(i * 10 + j, tens[i - 2] + " y " + units[j - 1]);
            } 
        }

        return IntStream.rangeClosed(10, 99)
            .mapToObj(n -> Arguments.of(n, inputNumberMap.get(n), outputNumberMap.get(n)));
    }
}

