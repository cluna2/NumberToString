package numbertostring.core.language.formatting;

import java.math.BigInteger;

public class EnglishGrammarFormatter implements LocalizedGrammarFormatter {


    public EnglishGrammarFormatter() {}


    @Override
    public String applyLanguageSpecificFormatting(
        String chunkString,
        BigInteger chunk, 
        String largeUnitString, 
        BigInteger largeUnit) {

            String regionalAdjustedString = ensureHundredUsage(chunkString, chunk);
            regionalAdjustedString = regionalAdjustedString + " " + largeUnitString + " ";
            return regionalAdjustedString.trim().replaceAll("\\s+", " ");
    }



    private String ensureHundredUsage(String numeralString, BigInteger chunk) {
        if (chunk.compareTo(BigInteger.valueOf(100)) >= 0 && chunk.compareTo(BigInteger.valueOf(1000)) < 0) {
            String[] words = numeralString.split("\\s+");
            String firstWord = words[0];

            // Append "hundred" after the first word
            words[0] = firstWord + " Hundred";

            // Reconstruct the numeral string
            return String.join(" ", words);
        }   
        return numeralString;
    }
}
