package numbertostring.converter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.language.LanguageRules;
import numbertostring.pojo.Number;

/**
 * Abstract class to convert numbers to word form using locale.
 * Subclasses are based on the type of number to convert.
 * Uses the {@code NumericalLanguageConstants} interface to retrieve constants
 * 
 * @param <T> Type of the Number to convert
 */
@Getter
@RequiredArgsConstructor
public abstract class LocalizedNumberConverter<T extends Number<T>> {

    /** Class defining rules for representing numbers for a given language.
     * @return constants type for language-specific constants
    */
    protected final LanguageRules rules;

    /**
     * Converts a Number to words.
     * Implementing classes must handle conversion using the LangauageRules field.
     * @param number Instance of Number
     * @param <T> Type of the number
     * @return Word representation in language of inherited locale.
     */
    public abstract <T extends Number<T>> String convertToWords(T number);


}
