package numbertostring.rules;

import java.util.Locale;

/** Interface tnat provides language specific conversion rules to a NumberConverter. */
public interface LanguageRulesProvider {
    /** Method to provide language-specific rules based on locale.
     * @param locale Locale to extract rules from.
     * @return LanguageRules object containing numeric conversion rules.
     */
    LanguageRules getLanguageRules(Locale locale);
}
