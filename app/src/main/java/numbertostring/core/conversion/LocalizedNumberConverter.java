package numbertostring.core.conversion;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.core.language.rules.LocalizedNumeralRules;
import numbertostring.core.model.Number;

/**
 * Abstract class to convert numbers to word form using locale.
 * Subclasses are based on the type of Number to convert.
 * 
 */
@Getter
@RequiredArgsConstructor
public abstract class LocalizedNumberConverter {

    /** Class defining rules for representing numbers for a given language.
     * @return Language-specific rules defining conversion logic.
    */
    protected final LocalizedNumeralRules rules;

    /**
     * Converts a Number to words.
     * Implementing classes must handle conversion using the rules field.
     * @param number Instance of a Number of type T
     * @return Word representation in language of inherited locale.
     */
    public abstract String convertToWords(Number<?> number);


}
