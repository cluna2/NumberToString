package numbertostring.core.language.rules;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import numbertostring.core.language.EnglishNumeralRules;
import numbertostring.core.language.SpanishNumeralRules;

/**
 * Implementation for LanguageRulesProvider. Responsible for creating a LanguageRules object.
 */
public class LanguageRulesRegistry  {
    private static final Map<String, LanguageRules> registry = new ConcurrentHashMap<>();
    /** Default constructor. */
    public LanguageRulesRegistry() {}
    
    /** Static block to preload supported languages */
    static {
        register("en", new LanguageRules(
            EnglishNumeralRules.SCALE_TYPE,
            EnglishNumeralRules.NEGATIVE_WORD,
            EnglishNumeralRules.NUMERALS,
            EnglishNumeralRules.LARGE_UNITS,
            EnglishNumeralRules.SMALL_NUMERAL_LOGIC,
            EnglishNumeralRules.LARGE_UNITS_LOGIC
        ));

        register("es", new LanguageRules(
            SpanishNumeralRules.SCALE_TYPE,
            SpanishNumeralRules.NEGATIVE_WORD,
            SpanishNumeralRules.NUMERALS,
            SpanishNumeralRules.LARGE_UNITS,
            SpanishNumeralRules.SMALL_NUMERAL_LOGIC,
            SpanishNumeralRules.LARGE_UNITS_LOGIC
        ));
    }

    /** Registers a new language rule in the registry */
    public static void register(String language, LanguageRules rules) {
        registry.put(language.toLowerCase(), rules);
    }
    /**
     * Returns a LanguageRules object depending on the Locale provided.
     * For locales with non-positional number systems, a function for custom rules support is used
     * and other entries are left null.
     * @param locale locale to create rules object for
     */
    public static LanguageRules getRules(Locale locale) {
        return registry.get(locale.getLanguage().toLowerCase());
    }

    /** Returns all available languages in the registry */
    public static Map<String, LanguageRules> getAllRules() {
        return registry;
    }
}
