package numbertostring.converter;

import java.util.Locale;
import numbertostring.pojo.Number;

public class IntegerNumConverter extends LocalizedNumberConverter{
    
    public IntegerNumConverter() {
        super(Locale.ENGLISH);  // Explicitly pass default locale
    }

    /**
     * Constructor with user-defined Locale.
     */
    public IntegerNumConverter(Locale locale) {
        super(locale);  // Explicitly pass user-defined locale
    }

    @Override
    public String convertToWords(Number number) {
        return "Hello";
    }

}
