package numbertostring.core.language;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import numbertostring.core.language.rules.EnglishNumeralRules;
import numbertostring.core.language.rules.LocalizedNumeralRules;
import numbertostring.core.language.rules.SpanishNumeralRules;



/**
 * Implementation for LanguageRulesProvider. Responsible for creating a LanguageRules object.
 */
public class LocalizedNumberRulesRegistry  {
    private static final Map<String, LocalizedNumeralRules> registry = new ConcurrentHashMap<>();    
    
    /** Default constructor. */
    private LocalizedNumberRulesRegistry() {}
    
    /** Static block to preload supported numeral systems */
    static {
        register("en", new EnglishNumeralRules());
        register("es", new SpanishNumeralRules());

    }

    /** Registers a new numeral rule implementation */
    public static void register(String language, LocalizedNumeralRules rules) {
        registry.put(language.toLowerCase(), rules);
    }

    /**
     * Retrieves numeral rules based on locale.
     * @param locale User's locale.
     * @return Corresponding LocalizedNumberRules implementation.
     */
    public static LocalizedNumeralRules getRules(Locale locale) {
        String key = locale.toLanguageTag().toLowerCase();
        return registry.getOrDefault(key, registry.get(locale.getLanguage().toLowerCase()));
    }

}
