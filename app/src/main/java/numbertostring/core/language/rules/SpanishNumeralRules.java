package numbertostring.core.language.rules;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;


import numbertostring.core.exception.NumeralRulesException;
import numbertostring.core.language.GroupingStrategy;
import numbertostring.core.model.NumberBase;

/**
 * Constants mapping number names to Spanish words.
 * Supports number-to-word conversion for {@code LocalizedNumberConverter}.
 */
public class SpanishNumeralRules extends LocalizedNumeralRules{


    /**
     * Rules object.
     */
    public SpanishNumeralRules() {}

    
    /** Map of ints to Spanish numeral words up to one hundred.
     * This map is "raw" in that proper grammar and formatting rules are not applied.
     */
    public static final TreeMap<Integer, String> NUMERALS = new TreeMap<>(Map.ofEntries(
        Map.entry(0, "Cero"), Map.entry(1, "Uno"), Map.entry(2, "Dos"), Map.entry(3, "Tres"),
        Map.entry(4, "Cuatro"), Map.entry(5, "Cinco"), Map.entry(6, "Seis"), Map.entry(7, "Siete"),
        Map.entry(8, "Ocho"), Map.entry(9, "Nueve"), Map.entry(10, "Diez"),
        Map.entry(11, "Once"), Map.entry(12, "Doce"), Map.entry(13, "Trece"), Map.entry(14, "Catorce"),
        Map.entry(15, "Quince"), Map.entry(16, "Dieciseis"), Map.entry(17, "Diecisiete"), Map.entry(18, "Dieciocho"),
        Map.entry(19, "Diecinueve"), Map.entry(20, "Veinte"), Map.entry(30, "Treinta"), Map.entry(40, "Cuarenta"),
        Map.entry(50, "Cincuenta"), Map.entry(60, "Sesenta"), Map.entry(70, "Setenta"), Map.entry(80, "Ochenta"),
        Map.entry(90, "Noventa"), Map.entry(100, "Cien"), Map.entry(200, "Doscientos"), Map.entry(300, "Trescientos"),
        Map.entry(400, "Cuatrocientos"), Map.entry(500, "Quinientos"), Map.entry(600, "Seiscientos"),
        Map.entry(700, "Setecientos"), Map.entry(800, "Ochocientos"), Map.entry(900, "Novecientos"),
        Map.entry(1000, "Mil")
    ));


    /** Map of large units to Spanish equivalents. The unit names are default pluaralized. */
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

    @Override
    public String getLanguageCode() {
        return "es".toLowerCase();
    }


    @Override
    public NumberBase getNumberBase() {
        return NumberBase.BASE_10;
    }
    
    @Override
    public GroupingStrategy getGroupingStrategy() {
        return GroupingStrategy.LONG_SCALE; 
    }
    @Override
    public String applyNumeralRulesForZero() {
        return "Cero";
    }

    @Override
    public String applyNegativeHandling(String numberString) {
        return "Menos " + numberString;
    }

    @Override
    public String applyNumeralRulesForSmallNumbers(int num) {
        if (num >= 1_000_000_000_000L) {
            throw new IllegalArgumentException("Number must be smaller than 1 billion for this method call.");
        }

        if (NUMERALS.containsKey(num)) {
            return NUMERALS.get(num);
        }

        StringBuilder result = new StringBuilder();

        // **Process Large Units First**
        for (BigInteger unit : LARGE_UNITS.keySet()) {
            if (num >= unit.intValue()) {
                int significantDigits = num / unit.intValue();
                int remainder = num % unit.intValue();

                String chunkString = applyNumeralRulesForSmallNumbers(significantDigits);
                String unitName = LARGE_UNITS.get(unit);

                // **Break Condition: Skip processing if chunk is "Cero"**
                if (!chunkString.equals("Cero")) {
                    result.append(chunkString).append(" ").append(unitName).append(" ");
                } else {
                    break;
                }
                
                num = remainder;
            }
        }

        // **Process Hundreds**
        if (num >= 100) {
            int hundreds = num / 100 * 100;
            num %= 100;
            result.append(NUMERALS.get(hundreds)).append(" "); // Leaves "Ciento" logic to formatter
        }

        // **Process Tens**
        if (num >= 20) {
            int tens = num / 10 * 10;
            num %= 10;
            result.append(NUMERALS.get(tens)).append(" "); // No "y" insertion here
        }

        // **Process Ones**
        if (num > 0) {
            result.append(NUMERALS.get(num)).append(" ");
        }

        return result.toString().trim();
    }


    // /**
    //  * Converts any number smaller than 1_000_000_000 to its Spanish word form representation.
    //  * One billion is chosen as the upper range as this is when the long scale applies for unit names.
    //  * @param num
    //  * @return
    //  */
    // @Override
    // public final String applyNumeralRulesForSmallNumbers(int num) {
    //     if (num > 1_000_000_000) {
    //         throw new IllegalArgumentException(String.format("Number is larger than 1 billion : %d", num));
    //     }

    //     if (num == 1_000_000_000) {
    //         return "Mil Millones";
    //     }

    //     StringBuilder result = new StringBuilder();
    //     if (num >= 1_000_000) {
    //         result.append(applyNumeralRulesForSmallNumbers(num / 100_000_000)).append("Millones ");
    //     }

    //     if (num >= 1_000) {
    //         String significantDigits = applyNumeralRulesForSmallNumbers(num / 1_000);
    //         // Remove "Uno" in front of "Mil"
    //         if (significantDigits.equals("Uno")) {
    //             result.append(" Mil ");
    //         } else {
    //             result.append(significantDigits).append(" Mil ");
    //         }
    //         num %= 1_000;   
    //     }

    //     if (num >= 100) {
    //         if (num == 100) {
    //             result.append(NUMERALS.get(num)).append(" ");
    //         } else if (num < 200) {
    //             result.append("Ciento").append(" ");
    //         } else {
    //             result.append(NUMERALS.floorEntry(num).getValue()).append(" ");
            
    //         }
    //         num %= 100;
    //     }

    //     if (num >= 30) {
    //         result.append(NUMERALS.floorEntry(num).getValue()).append(" Y ");
    //         num %= 10;
    //     } else if (num >= 20) {
    //         // Numbers from 20-29 are of the form "Veinti#" for a number #
    //         result.append("Veinti");
    //         num -= 20;
    //         if (num == 0) {
    //             result.append(" ");
    //         } else {
    //             result.append(NUMERALS.get(num).toLowerCase()).append(" ");
    //             num = 0;
    //         }
    //     }  else if (num >= 10) {
    //         result.append(NUMERALS.floorEntry(num - (num % 10)).getValue()).append(" ");
    //         num = 0;
    //     }
        
    //     if (num > 0) {
    //         result.append(NUMERALS.get(num)).append(" ");
    //     }
    //     return result.toString().trim();
    // }

    /**  */

    // public final String applyNumeralRulesForLargeUnits(String chunkString)  {
    //     BigInteger largeUnit = BigInteger.ZERO;
    //     String modifiedChunkString = chunkString;
    //     if (LARGE_UNITS.containsKey(largeUnit)) {
    //         String unitName = LARGE_UNITS.get(largeUnit);
    //         int exponent = BigIntegerMath.log10(largeUnit, RoundingMode.UNNECESSARY);
    //         // Apply the following rules if the chunk is just 1
    //         if (chunkString.equals("Uno")) {
    //             // Powers of 10^6  require the singular "Un".
    //             // The unit name is also made singular
    //             if (exponent % 6 == 0) {
    //                 String singularUnitName = SINGULAR_LARGE_UNITS.getOrDefault(unitName, unitName);
    //                 modifiedChunkString = "Un " + singularUnitName + " ";
    //             // 1000 itself is just "Mil"
    //             } else if (exponent == 3) {
    //                 modifiedChunkString = "Mil";
    //             // Otherwise, just append the appropriate unit
    //             } else {
    //                 modifiedChunkString = chunkString + LARGE_UNITS.get(largeUnit); 
    //             }
    //         } else {
    //             modifiedChunkString = chunkString + " " + LARGE_UNITS.get(largeUnit) + " ";
    //         }
    //     }
    //     return modifiedChunkString.trim();
    // }

    @Override
    public String applyNonPositionalConversion(BigInteger num) {
       throw new NumeralRulesException("English does not support non-positional number conversion.");
    }


    @Override
    public String getLargeUnitName(BigInteger largeUnit) {
        return LARGE_UNITS.getOrDefault(largeUnit, "");
    }

}
