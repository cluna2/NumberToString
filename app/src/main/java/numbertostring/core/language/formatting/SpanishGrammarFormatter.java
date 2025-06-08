package numbertostring.core.language.formatting;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanishGrammarFormatter implements LocalizedGrammarFormatter {

    private static final Map<String, String> ACCENT_MAP = new HashMap<>();

    static {
        ACCENT_MAP.put("Dieciseis", "Dieciséis");
        ACCENT_MAP.put("Veinte Dos ", "Veintidós ");
        ACCENT_MAP.put("Veinte Tres ", "Veintitrés ");
        ACCENT_MAP.put("Veinte Seis ", "Veintiséis ");
    }

    public SpanishGrammarFormatter() {}

    @Override
    public String applyLanguageSpecificFormatting(
        String chunkString, 
        BigInteger chunk,
        String unitName,
        BigInteger unit) {

        chunkString = ensureHundredUsage(chunkString);
        chunkString = applyLargeUnitFormatting(chunkString, unitName, unit);
        chunkString = applyGrammarAdjustments(chunkString);
        return chunkString.trim().replaceAll("\\s+", " ");
        
    }

    /** **Fix "Ciento" vs. "Cien" Usage**
     * "Cien" should be "Ciento" unless it stands alone (e.g., "Cien Mil" vs. "Ciento Uno").
     */
    private String ensureHundredUsage(String chunkString) {
        return chunkString.replaceAll("\\bCien (?!Mil)\\b", "Ciento ");
    }

    /** **Properly Append Large Unit Names**
     * Ensures "Uno Mil" is converted to "Mil", and "Uno Millones" to "Un Millón".
     */
    public String applyLargeUnitFormatting(String chunkString, String unitName, BigInteger unit) {

        if (chunkString.equals("Uno") && unit.equals(BigInteger.valueOf(1_000))) {
            return unitName; // Output "Mil" alone
        }

        if (chunkString.equals("Uno") && unitName.endsWith("es")) {
            unitName = unitName.substring(0, unitName.length() - 2); // Convert "Millones" → "Millón"
            return "Un " + unitName;
        }

        return chunkString + " " + unitName;
    }

    /** Applies Spanish grammar rules to numerals
     * Ensures "Treinta Uno" → "Treinta y Uno" where applicable.
     */
    private String applyGrammarAdjustments(String chunkString) {

        // Fix tens-and-ones phrasing (e.g., "Treinta Uno" → "Treinta y Uno")
        chunkString = chunkString.replaceAll(
            "\\b(Treinta|Cuarenta|Cincuenta|Sesenta|Setenta|Ochenta|Noventa) (Uno|Dos|Tres|Cuatro|Cinco|Seis|Siete|Ocho|Nueve)\\b",
            "$1 y $2"
        );

        // Apply accents
        for (Map.Entry<String, String> entry : ACCENT_MAP.entrySet()) {
            chunkString = chunkString.replace(entry.getKey(), entry.getValue());
        }
        
        Pattern pattern = Pattern.compile("\\bVeinte\\s(\\w)");
        Matcher matcher = pattern.matcher(chunkString);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, "Veinti" + matcher.group(1).toLowerCase());
        }
        matcher.appendTail(result);
        
        return result.toString().trim();
    }
}