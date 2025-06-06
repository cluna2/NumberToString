package numbertostring.core.model;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.language.rules.LanguageRules;


@RequiredArgsConstructor
/**
 * Generic class to represent a number across different numeric systems.
 * Uses BigDecimals to represent the underlying value in base 10 to provide flexiblility
 * for subclass implementations.
 * 
 * @param <T> The type of Number. 
 */
public abstract class Number<T extends Number<T>> {
    
    /** Retrieves appropriate converter for the Number type
     * Guranteed to return correct type as each Number knows its own 
     * Converter type. (e.g. IntegerNums return IntegerNumConverters)
     * 
     * @param rules set of language-specific rules for Converter instance
     * @return LocalizedNumberConverter of type T
     */
    public abstract LocalizedNumberConverter getConverter(LanguageRules rules);

    /** Underlying value of number in base 10. Stored using BigDecimal. */
    protected final BigDecimal value;

    private final Class<T> type;
    /**
     * Retrieves the underlying number type.
     * @return Type T of the Number.
     */
    public Class<T> getType() {
        return type;
    }

    

}
