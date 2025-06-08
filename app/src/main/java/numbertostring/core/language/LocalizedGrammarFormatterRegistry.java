package numbertostring.core.language;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import numbertostring.core.language.formatting.EnglishGrammarFormatter;
import numbertostring.core.language.formatting.LocalizedGrammarFormatter;
import numbertostring.core.language.formatting.SpanishGrammarFormatter;

public class LocalizedGrammarFormatterRegistry {
    private static final Map<String, LocalizedGrammarFormatter> registry = new ConcurrentHashMap<>(); 
    

    // **Static block to preload supported formatters**
    static {
        register("en", new EnglishGrammarFormatter());
        register("es", new SpanishGrammarFormatter());
    }

    /** Registers a new formatter */
    public static void register(String language, LocalizedGrammarFormatter formatter) {
        registry.put(language.toLowerCase(), formatter);
    }

    /** Retrieves formatter based on language code */
    public static LocalizedGrammarFormatter getFormatter(Locale locale) {
        return registry.getOrDefault(locale.getLanguage().toLowerCase(), null);
    }
}

