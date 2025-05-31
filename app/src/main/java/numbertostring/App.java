package numbertostring;

import java.math.BigInteger;

import numbertostring.api.NumberToWordsAPI;
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
            BigInteger value = BigInteger.valueOf(21);
            IntegerNum num = IntegerNum.builder()
                .value(value)
                .build();
            String words = NumberToWordsAPI.convertNumberToWords(num);
            System.out.println(words);
 
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
