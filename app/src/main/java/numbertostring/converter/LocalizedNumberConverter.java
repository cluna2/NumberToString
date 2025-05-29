package numbertostring.converter;


import java.util.Locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.pojo.Number;

@Getter
@RequiredArgsConstructor
public abstract class LocalizedNumberConverter {

    // Desired locale for number converstion
    private final Locale locale;

    /**
     * Converts a Number to words.
     * Implementing classes 
     *
     * @param number Instance of Number
     * @return Word representation in English (default)
     */
    public abstract String convertToWords(Number number);

}
