package numbertostring.core.converter;


import org.junit.jupiter.api.Test;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.utils.IntegerNumConverterTestHelper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerNumConverterSpanishTest {

    private final IntegerNumConverter converter = IntegerNumConverterTestHelper.createConverter(Locale.of("es", "ES"));

    @Test
    void testParsesIntMinValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Integer.MIN_VALUE),
            "Menos Dos Mil Ciento Cuarenta Y Siete Millones Cuatrocientos Ochenta Y Tres Mil Seiscientos Cuarenta Y Ocho");
    }

    @Test
    void testParsesIntMaxValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Integer.MAX_VALUE),
            "Dos Mil Ciento Cuarenta Y Siete Millones Cuatrocientos Ochenta Y Tres Mil Seiscientos Cuarenta Y Siete");
    }
        
    @Test
    void testParsesLongMinValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Long.MIN_VALUE),
            "Menos Nueve Trillones Doscientos Veintitres Mil "
                                + "Trescientos Setenta Y Dos Billones "
                                + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                                + "Setecientos Setenta Y Cinco Mil Ochocientos Ocho");
    }

    @Test
    void testParsesLongMaxValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Long.MAX_VALUE),
            "Nueve Trillones Doscientos Veintitres Mil "
                               + "Trescientos Setenta Y Dos Billones "
                               + "Treinta Y Seis Mil Ochocientos Cincuenta Y Cuatro Millones "
                               + "Setecientos Setenta Y Cinco Mil Ochocientos Siete");
    }

    @Test
    void testParsesLargeUnits() {
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
        List<BigInteger> values = IntStream.range(0, expectedStrings.size())
            .mapToObj(i -> BigInteger.valueOf((long)Math.pow(10, i)))
            .collect(Collectors.toList());
        IntegerNumConverterTestHelper.assertConversionList(converter, values, expectedStrings);
    }


}
