package numbertostring;

import java.math.BigDecimal;
import java.util.Locale;

import numbertostring.api.NumberToWordsService;
import numbertostring.api.dto.NumberToWordsRequest;
import numbertostring.api.dto.NumberToWordsResponse;

/**
 * Simple App to call conversion API. 
 */
public class App {

    /**
     * App object
     * 
     */
    public App() {}

    /**
     * Sample call using Integer wrapper
     * @param args none
     */
    public static void main(String[] args) {
        try {
            BigDecimal value = BigDecimal.valueOf(Integer.MIN_VALUE);
            NumberToWordsService api = new NumberToWordsService();
            Locale locale = Locale.of("es", "ES");
            NumberToWordsRequest req = NumberToWordsRequest.builder()
                .numberValue(value)
                .locale(locale)
                .build();
            NumberToWordsResponse response = api.convertNumberToWordsWithLocale(req);
            System.out.println(response.getConvertedData().getConvertedText());
 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
