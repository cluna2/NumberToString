package numbertostring;

import java.math.BigInteger;
import java.util.Locale;

import numbertostring.api.NumberToWordsService;
import numbertostring.dto.NumberToWordsRequest;
import numbertostring.dto.NumberToWordsResponse;
import numbertostring.pojo.IntegerNum;

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
            BigInteger value = BigInteger.valueOf(Integer.MIN_VALUE);

            // BigInteger value = BigInteger.valueOf(Integer.MIN_VALUE - 1_000_000_000);
            // BigInteger value =new  BigInteger("100000");
            IntegerNum num = new IntegerNum(value);
            NumberToWordsService api = NumberToWordsService.create();
            Locale locale = Locale.of("es", "ES");
            NumberToWordsRequest<IntegerNum> req = NumberToWordsRequest.<IntegerNum>builder()
                .number(num)
                .locale(locale)
                .build();
            NumberToWordsResponse response = api.convertNumberToWordsWithLocale(req);
            System.out.println(response.getWords());
 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
