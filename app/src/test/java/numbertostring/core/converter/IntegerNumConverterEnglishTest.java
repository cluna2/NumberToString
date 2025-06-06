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

public class IntegerNumConverterEnglishTest {

    private final IntegerNumConverter converter = IntegerNumConverterTestHelper.createConverter(Locale.ENGLISH);

    @Test
    void testParsesZeroValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.ZERO, "Zero");
    }

    @Test
    void testParsesIntMinValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Integer.MIN_VALUE),
            "Negative Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Eight");
    }

    @Test
    void testParsesIntMaxValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Integer.MAX_VALUE),
            "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven");
    }

    @Test
    void testParsesLongMinValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Long.MIN_VALUE), 
            "Negative Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Eight");
    }

    @Test
    void testParsesLongMaxValue() {
        IntegerNumConverterTestHelper.assertConversion(converter, BigInteger.valueOf(Long.MAX_VALUE), 
            "Nine Quintillion Two Hundred Twenty Three Quadrillion "
                      + "Three Hundred Seventy Two Trillion Thirty Six Billion "
                      + "Eight Hundred Fifty Four Million Seven Hundred Seventy Five "
                      + "Thousand Eight Hundred Seven");
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
        List<BigInteger> values = IntStream.range(0, expectedStrings.size())
            .mapToObj(i -> BigInteger.valueOf((long)Math.pow(10, i)))
            .collect(Collectors.toList());
        IntegerNumConverterTestHelper.assertConversionList(converter, values, expectedStrings);
    }

}
