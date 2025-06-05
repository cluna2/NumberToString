package numbertostring.language;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.math.BigIntegerMath;

import numbertostring.language.NumberGrouping.ScaleType;

/**
 * Constants mapping number names to Spanish words.
 * Supports number-to-word conversion for {@code LocalizedNumberConverter}.
 */
public class SpanishNumeralRules {

    /**
     * Private constructor to prevent instantiation.
     */
    private SpanishNumeralRules() {}

    /** Spanish uses the Long scale for grouping large numbers. */
    public static final ScaleType SCALE_TYPE = ScaleType.LONG_SCALE;

    /** Constant for negative numbers in Spanish. */
    public static final String NEGATIVE_WORD = "Menos";

    /** Map of ints to Spanish numeral words up to one hundred. */
    public static final TreeMap<Integer, String> NUMERALS = new TreeMap<>(Map.ofEntries(
        Map.entry(0, "Cero"), Map.entry(1, "Uno"), Map.entry(2, "Dos"), Map.entry(3, "Tres"),
        Map.entry(4, "Cuatro"), Map.entry(5, "Cinco"), Map.entry(6, "Seis"), Map.entry(7, "Siete"),
        Map.entry(8, "Ocho"), Map.entry(9, "Nueve"), Map.entry(10, "Diez"),
        Map.entry(11, "Once"), Map.entry(12, "Doce"), Map.entry(13, "Trece"), Map.entry(14, "Catorce"),
        Map.entry(15, "Quince"), Map.entry(16, "Dieciséis"), Map.entry(17, "Diecisiete"), Map.entry(18, "Dieciocho"),
        Map.entry(19, "Diecinueve"), Map.entry(20, "Veinte"), Map.entry(30, "Treinta"), Map.entry(40, "Cuarenta"),
        Map.entry(50, "Cincuenta"), Map.entry(60, "Sesenta"), Map.entry(70, "Setenta"), Map.entry(80, "Ochenta"),
        Map.entry(90, "Noventa"), Map.entry(100, "Cien"), Map.entry(200, "Doscientos"), Map.entry(300, "Trescientos"),
        Map.entry(400, "Cuatrocientos"), Map.entry(500, "Quinientos"), Map.entry(600, "Seiscientos"),
        Map.entry(700, "Setecientos"), Map.entry(800, "Ochocientos"), Map.entry(900, "Novecientos")
    ));


    /** Map of large units to Spanish equivalents. */
    public static final Map<BigInteger, String> LARGE_UNITS = new TreeMap<>(Map.ofEntries(
        Map.entry(BigInteger.valueOf(1_000), "Mil"),
        Map.entry(BigInteger.valueOf(1_000_000), "Millones"),
        Map.entry(BigInteger.valueOf(1_000_000_000), "Mil Millones"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000L), "Billones"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000L), "Mil Billones"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000_000L), "Trillones"),
        Map.entry(new BigInteger("1000000000000000000000"), "Mil Trillones"),
        Map.entry(new BigInteger("1000000000000000000000000"), "Cuatrillones"),
        Map.entry(new BigInteger("1000000000000000000000000000"), "Mil Cuatrillones"),
        Map.entry(new BigInteger("1000000000000000000000000000000"), "Quintillones")
    ));

    
    /**
     * Converts any number smaller than 1_000_000_000 to its Spanish word form representation.
     * One billion is chosen as the upper range as this is when the long scale applies for unit names.
     * @param num
     * @return
     */
    public static final String convertNumbersBelowBillion (int num) {
        if (num > 1_000_000_000) {
            throw new IllegalArgumentException(String.format("Number is larger than 1 billion : %d", num));
        }

        if (num == 1_000_000_000) {
            return "Mil Millones";
        }

        StringBuilder result = new StringBuilder();
        if (num >= 1_000_000) {
            result.append(convertNumbersBelowBillion(num / 100_000_000)).append("Millones ");
        }

        if (num >= 1_000) {
            String significantDigits = convertNumbersBelowBillion(num / 1_000);
            // Remove "Uno" in front of "Mil"
            if (significantDigits.equals("Uno")) {
                result.append(" Mil ");
            } else {
                result.append(significantDigits).append(" Mil ");
            }
            num %= 1_000;   
        }

        if (num >= 100) {
            if (num == 100) {
                result.append(NUMERALS.get(num)).append(" ");
            } else if (num < 200) {
                result.append("Ciento").append(" ");
            } else {
                result.append(NUMERALS.floorEntry(num).getValue()).append(" ");
            
            }
            num %= 100;
        }

        if (num >= 30) {
            result.append(NUMERALS.floorEntry(num).getValue()).append(" Y ");
            num %= 10;
        } else if (num >= 20) {
            // Numbers from 20-29 are of the form "Veinti#" for a number #
            result.append("Veinti");
            num -= 20;
            if (num == 0) {
                result.append(" ");
            } else {
                result.append(NUMERALS.get(num).toLowerCase()).append(" ");
                num = 0;
            }
        }  else if (num >= 10) {
            result.append(NUMERALS.floorEntry(num - (num % 10)).getValue()).append(" ");
            num = 0;
        }
        
        if (num > 0) {
            result.append(NUMERALS.get(num)).append(" ");
        }
        return result.toString().trim();
    }


    public static final Function<Integer, String> SMALL_NUMERAL_LOGIC = SpanishNumeralRules::convertNumbersBelowBillion;

    /**  */
    public static final BiFunction<String, BigInteger, String> LARGE_UNITS_LOGIC = (chunkString, largeUnit) -> {
        String modifiedChunkString = chunkString;
        if (LARGE_UNITS.containsKey(largeUnit)) {
            String unitName = LARGE_UNITS.get(largeUnit);
            int exponent = BigIntegerMath.log10(largeUnit, RoundingMode.UNNECESSARY);
            // Apply the following rules if the chunk is just 1
            if (chunkString.equals("Uno")) {
                // Powers of 10^6  require the singular "Un".
                // The unit name is also made singular
                if (exponent % 6 == 0 && exponent != 1) {
                    String singularUnitName = unitName.substring(0, unitName.length() - 2);
                    modifiedChunkString = "Un " + singularUnitName + " ";
                // 1000 itself is just "Mil"
                } else if (exponent == 3) {
                    modifiedChunkString = "Mil";
                // Otherwise, just append the appropriate unit
                } else {
                    modifiedChunkString = chunkString + LARGE_UNITS.get(largeUnit); 
                }
            } else {
                modifiedChunkString = chunkString + " " + LARGE_UNITS.get(largeUnit) + " ";
            }
        }
        return modifiedChunkString.trim();
    };
}
