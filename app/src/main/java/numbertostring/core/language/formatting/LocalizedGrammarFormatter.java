package numbertostring.core.language.formatting;

import java.math.BigInteger;

public interface LocalizedGrammarFormatter {

    /** Applies regional variations or phrasing adjustments */
    public String applyLanguageSpecificFormatting(
        String chunkString,
        BigInteger chunk, 
        String unitName, 
        BigInteger largeUnit);

}

