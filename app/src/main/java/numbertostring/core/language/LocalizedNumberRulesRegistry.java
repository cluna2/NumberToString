package numbertostring.core.language;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import numbertostring.core.language.rules.EnglishNumeralRules;
import numbertostring.core.language.rules.LocalizedNumberRules;
import numbertostring.core.language.rules.SpanishNumeralRules;



/**
 * Implementation for LanguageRulesProvider. Responsible for creating a LanguageRules object.
 */
public class LocalizedNumberRulesRegistry  {
    private static final Map<String, LocalizedNumberRules> registry = new ConcurrentHashMap<>();    
    
    /** Default constructor. */
    private LocalizedNumberRulesRegistry() {}
    
    /** Static block to preload supported numeral systems */
    static {
        register("en", new EnglishNumeralRules());
        register("es", new SpanishNumeralRules());
    }

    /** Registers a new numeral rule implementation */
    public static void register(String language, LocalizedNumberRules rules) {
        registry.put(language.toLowerCase(), rules);
    }

    /** Returns the numeral rules for a given locale */
    public static LocalizedNumberRules getRules(Locale locale) {
        return registry.get(locale.getLanguage().toLowerCase());
    }

}
