package numbertostring.pojo;

import java.math.BigDecimal;

import org.checkerframework.checker.units.qual.C;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.language.LanguageRules;


@SuperBuilder
@RequiredArgsConstructor
/**
 * Generic class to represent a number across different numeric systems.
 * Uses BigDecimals to represent the underlying value in base 10.
 * 
 * @param <T> The type of Number. 
 */
public abstract class Number<T extends Number<T>> {
    
    /** Retrieves appropriate converter for the Number type T.
     * Guranteed to return correct type as each Number knows its own 
     * Converter type. (e.g. IntegerNums return IntegerNumConverters)
     * 
     * @param rules set of language-specific rules for Converter instance
     * @return LocalizedNumberConverter of type T
     */
    public abstract LocalizedNumberConverter<T> getConverter(LanguageRules rules);

    /** Underlying value of number in base 10. Stored using BigDecimal.
     * @param value the value of this number
     * */
    protected final BigDecimal value;



}
