package numbertostring.converter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.language.LanguageRules;
import numbertostring.pojo.Number;

/**
 * Abstract class to convert numbers to word form using locale.
 * Subclasses are based on the type of Mumber to convert.
 * Uses the {@code LanguageProvider} interface to retrieve language rules.
 * 
 * @param <T> Type of the Number to convert
 */
@Getter
@RequiredArgsConstructor
public abstract class LocalizedNumberConverter<T extends Number<T>> {

    /** Class defining rules for representing numbers for a given language.
     * @return Language-specific rules defining conversion logic.
    */
    protected final LanguageRules rules;

    /**
     * Converts a Number to words.
     * Implementing classes must handle conversion using the rules field.
     * @param number Instance of a Number of type T
     * @return Word representation in language of inherited locale.
     */
    public abstract String convertToWords(Number<T> number);


}
